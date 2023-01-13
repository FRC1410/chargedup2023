package org.frc1410.framework.scheduler.task;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.scheduler.loop.Loop;
import org.frc1410.framework.scheduler.loop.LoopStore;
import org.frc1410.framework.scheduler.task.impl.CommandTask;
import org.frc1410.framework.scheduler.task.lock.LockHandler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public final class TaskScheduler {

    public final LoopStore loops = new LoopStore(this);
    public final LockHandler lockHandler = new LockHandler();

    public void schedule(@NotNull Task job, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority, @NotNull Loop loop) {
        var lock = job.getLockKeys().isEmpty() ? null : new TaskLock(lockPriority, job.getLockKeys());
        var task = new BoundTask(new LifecycleHandle(), job, persistence, observer, lock);

        schedule(task, loop);
    }

    public void schedule(@NotNull Task job, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority, long period) {
        schedule(job, persistence, observer, lockPriority, loops.ofPeriod(period));
    }

    public void schedule(@NotNull Task job, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority) {
        schedule(job, persistence, observer, lockPriority, loops.main);
    }

    // Registers tasks to the default loop.
    public void schedule(@NotNull Task job, @NotNull TaskPersistence persistence) {
        schedule(job, persistence, Observer.DEFAULT, LockPriority.NULL, loops.main);
    }

    public void scheduleDefaultCommand(@NotNull Command command, @NotNull TaskPersistence persistence) {
        schedule(new CommandTask(command), persistence, Observer.DEFAULT, LockPriority.LOWEST);
    }

    public void scheduleDefaultCommand(@NotNull Command command, @NotNull TaskPersistence persistence, long period) {
        schedule(new CommandTask(command), persistence, Observer.DEFAULT, LockPriority.LOWEST, period);
    }

    public void scheduleAutoCOmmand(@NotNull Command command) {
        schedule(new CommandTask(command), TaskPersistence.EPHEMERAL, Observer.NO_OP, LockPriority.HIGH);
    }

    public void scheduleAutoCommand(@NotNull Command command, long period) {
        schedule(new CommandTask(command), TaskPersistence.EPHEMERAL, Observer.NO_OP, LockPriority.HIGH, period);
    }

    private void schedule(BoundTask task, Loop loop) {
        loop.add(task);
    }

    public LoopStore getLoopStore() {
        return loops;
    }
}
