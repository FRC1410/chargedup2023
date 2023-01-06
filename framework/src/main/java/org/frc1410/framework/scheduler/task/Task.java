package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Task {

    default void init() {

    }

    void execute();

    boolean isFinished();

    default void end(boolean interrupted) {
        
    }

    @Nullable
    default Object getLockKey() {
        return null;
    }

    default BoundTask bind(@NotNull TaskPersistence persistence, @NotNull Observer observer, int priority) {
        var lockKey = getLockKey();
        if (lockKey != null) {
            return new BoundTask(this, persistence, observer, new TaskLock(priority, lockKey));
        }

        return new BoundTask(this, persistence, observer, null);
    }
}