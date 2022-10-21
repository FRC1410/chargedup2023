package org.frc1410.framework.scheduler.loop;

import org.frc1410.framework.scheduler.task.BoundTask;
import org.frc1410.framework.scheduler.task.Task;
import org.frc1410.framework.scheduler.task.TaskState;

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

    private final long period;
    private final Set<BoundTask> tasks = new HashSet<>();

    Loop(long period) {
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
        tasks.forEach(Loop::process);
    }

    private static void process(BoundTask task) {
        Task job = task.job;
        switch (task.state) {
            case FLAGGED_EXECUTION -> {
                job.init();
                task.state = TaskState.EXECUTING;
            }

            case EXECUTING -> {
                job.execute();
                if (job.isFinished()) {
                    task.state = TaskState.FLAGGED_FINISH;
                }
            }

            case FLAGGED_FINISH -> {
                job.end(false);
                task.state = TaskState.ENDED;
            }

            case FLAGGED_CANCELLATION -> {
                job.end(true);
                task.state = TaskState.ENDED;
            }
        }
    }
}
