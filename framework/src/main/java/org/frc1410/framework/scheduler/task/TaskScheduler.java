package org.frc1410.framework.scheduler.task;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.scheduler.loop.Loop;
import org.frc1410.framework.scheduler.loop.LoopStore;
import org.frc1410.framework.scheduler.task.lock.LockHandler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public class TaskScheduler {

    public final LoopStore loops = new LoopStore(this);
    public final LockHandler lockHandler = new LockHandler();

    public void schedule(@NotNull Task task, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority, @NotNull Loop loop) {
        schedule(task.bind(persistence, observer, lockPriority), loop);
    }

    public void schedule(@NotNull Task task, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority, long period) {
        schedule(task, persistence, observer, lockPriority, loops.ofPeriod(period));
    }

    public void schedule(@NotNull Task task, @NotNull TaskPersistence persistence, @NotNull Observer observer, @MagicConstant(valuesFromClass = LockPriority.class) int lockPriority) {
        schedule(task, persistence, observer, lockPriority, loops.main);
    }

    // Registers tasks to the default loop.
    public void schedule(@NotNull Task task, @NotNull TaskPersistence persistence) {
        schedule(task, persistence, Observer.DEFAULT, LockPriority.NULL, loops.main);
    }

    public void scheduleDefaultCommand(@NotNull Command command, @NotNull TaskPersistence persistence) {
        schedule(new CommandTask(command), persistence, Observer.DEFAULT, LockPriority.LOWEST);
    }

    private void schedule(BoundTask task, Loop loop) {
        loop.add(task);
    }

    public LoopStore getLoopStore() {
        return loops;
    }
}
