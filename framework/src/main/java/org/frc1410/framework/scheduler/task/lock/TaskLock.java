package org.frc1410.framework.scheduler.task.lock;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record TaskLock(@MagicConstant(valuesFromClass = LockPriority.class) int priority, @NotNull List<?> keys) {

    public TaskLock(@MagicConstant(valuesFromClass = LockPriority.class) int priority, List<?> keys) {
        this.priority = priority;
        this.keys = List.copyOf(keys);
    }
}