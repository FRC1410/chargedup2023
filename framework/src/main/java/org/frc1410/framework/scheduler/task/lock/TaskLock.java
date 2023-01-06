package org.frc1410.framework.scheduler.task.lock;

import org.intellij.lang.annotations.MagicConstant;

public class TaskLock {

    @MagicConstant(valuesFromClass = LockPriority.class)
    public final int priority;
    public final int key;

    public TaskLock(@MagicConstant(valuesFromClass = LockPriority.class) int priority, int key) {
        this.priority = priority;
        this.key = key;
    }

    public TaskLock(@MagicConstant(valuesFromClass = LockPriority.class) int priority, Object key) {
        this(priority, System.identityHashCode(key));
    }

    @Override
    public int hashCode() {
        return key;
    }
}