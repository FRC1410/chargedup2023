package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Task {

    default void init() {

    }

    void execute();

    boolean isFinished();

    default void end(boolean interrupted) {
        
    }

    default @NotNull List<@NotNull Object> getLockKeys() {
        return List.of();
    }

    @Nullable
    @Deprecated
    default Object getLockKey() {
        return null;
    }

    default BoundTask bind(@NotNull TaskPersistence persistence, @NotNull Observer observer, int priority) {
        var lockKeys = getLockKeys();
        if (!lockKeys.isEmpty()) {
            return new BoundTask(new LifecycleHandle(), this, persistence, observer, new TaskLock(priority, lockKeys));
        }

        return new BoundTask(new LifecycleHandle(), this, persistence, observer, null);
    }
}