package org.frc1410.framework.scheduler.loop;

import org.frc1410.framework.phase.Phase;
import org.frc1410.framework.scheduler.task.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Loops hold bound tasks, and are ticked on their period. There is
 * also a default loop, that runs on the normal robot periodic tick
 * and is generally used for most tasks.
 *
 * <p>These loops are <i>not</i> the same as the loops found in 254's
 * code, for example, but they are similar. They do not get their own
 * thread, but they do handle execution of tasks.
 *
 * @see LoopStore#main
 */
public class Loop {

    private final TaskScheduler scheduler;
    private final Set<BoundTask> tasks = new HashSet<>();
    private final long period;
    private boolean disabled;

    public Loop(TaskScheduler scheduler, long period) {
        this.scheduler = scheduler;
        this.period = period;
    }

    public long getPeriod() {
        return period;
    }

    public double getPeriodSeconds() {
        return getPeriod() / 1000d;
    }

    public void add(BoundTask task) {
        tasks.add(task);
    }

    public void tick() {
        if (disabled) {
            return;
        }

        // Remove any tasks flagged for termination. If we were to do this in the process sycle, we would get CMEs.
        tasks.removeIf(task -> task.lifecycle.state == TaskState.TERMINATED);
        // Tick any tasks registered to this loop.
        tasks.forEach(this::process);
    }

    public void flagTransition(Phase newPhase) {
        disabled = newPhase == Phase.DISABLED;

        for (var task : tasks) {
            if (!task.persistence.shouldPersist(newPhase)) {
                task.lifecycle.requestInterruption();
            }
        }
    }

    private void process(BoundTask task) {
        // Just skip this iteration entirely if the task lock is claimed. This prevents fun things like
        // a case where an observer resumes execution of a task despite its lock being actively in use.
        if (!scheduler.lockHandler.ownsLock(task)) {
            return;
        }

        // Tick the observer to update the task state.
        task.observer.tick(task.lifecycle);

        Task job = task.job;
        LifecycleHandler lifecycle = task.lifecycle;

        switch (lifecycle.state) {
            case FLAGGED_EXECUTION -> {
                job.init();
                lifecycle.state = TaskState.EXECUTING;
            }

            case EXECUTING -> {
                job.execute();
                
                if (job.isFinished()) {
                    lifecycle.state = TaskState.FLAGGED_COMPLETION;
                }
            }

            case FLAGGED_COMPLETION -> {
                job.end(false);
                lifecycle.state = TaskState.SUSPENDED;

                scheduler.lockHandler.releaseLock(task);
            }

            case FLAGGED_SUSPENSION -> {
                job.end(true);
                lifecycle.state = TaskState.SUSPENDED;

                scheduler.lockHandler.releaseLock(task);
            }

            case FLAGGED_TERMINATION -> {
                job.end(true);
                lifecycle.state = TaskState.TERMINATED;

                scheduler.lockHandler.releaseLock(task);
            }
        }
    }
}
