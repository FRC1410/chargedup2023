package org.frc1410.framework.scheduler.task.lock;

import org.frc1410.framework.scheduler.task.BoundTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
* Developers will often encounter an issue where two commands (which are backed by tasks internally)
* depend on the same shared resource, generally in the form of a subsystem. In order to prevent race
* conditions and unpredictable behavior, tasks can acquire locks. When a task acquires a lock, the
* task with the lowest priority will be suspended. Once the task that claimed possession of a lock
* finishes (whether by cancellation or interruption), execution of the other task that laid claim
* on the lock is rescheduled for execution.
*/
public class LockHandler {

    private final Map<Object, BoundTask> locks = new ConcurrentHashMap<>();

    /**
     * Checks if a lock is owned by the given task. Has the additional
     * effect of giving ownership of the lock key to the provided task
     * if none is assigned.
     *
     * <p>When a task has prior claim over a lock key but is of lower
     * priority than the given task, access to the lock is handed to
     * the new task.
     *
     * @param task The task to check
     *
     * @return {@code true} if the provided task owns the
     *         lock.
     */
    public boolean ownsLock(BoundTask task) {
        if (task.lock == null) return true;

        var owner = locks.putIfAbsent(task.lock.key, task);

        if (owner == null || owner == task) return true;
        if (owner.lock == null) return true;

        if (task.lock.priority > owner.lock.priority) {
            locks.put(task.lock.key, task);
            return true;
        }

        return false;
    }

    public void releaseLock(BoundTask task) {
        if (task.lock != null) {
            locks.remove(task.lock.key, task);
        }
    }
}