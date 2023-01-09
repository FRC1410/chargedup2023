package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record BoundTask(@NotNull LifecycleHandle handle, @NotNull Task job, @NotNull TaskPersistence persistence,
                        @NotNull Observer observer, @Nullable TaskLock lock) {
    public BoundTask {
        Objects.requireNonNull(handle);
        Objects.requireNonNull(job);
        Objects.requireNonNull(persistence);
        Objects.requireNonNull(observer);
    }
}