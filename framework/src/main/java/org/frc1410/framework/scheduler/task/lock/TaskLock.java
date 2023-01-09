package org.frc1410.framework.scheduler.task.lock;

import org.intellij.lang.annotations.MagicConstant;

import java.util.List;

public class TaskLock {

    @MagicConstant(valuesFromClass = LockPriority.class)
    public final int priority;
    public final int[] keys;

    public TaskLock(@MagicConstant(valuesFromClass = LockPriority.class) int priority, List<Object> keys) {
        this.priority = priority;

        var keyHashes = new int[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            keyHashes[i] = System.identityHashCode(keys.get(i));
        }

        this.keys = keyHashes;
    }
}