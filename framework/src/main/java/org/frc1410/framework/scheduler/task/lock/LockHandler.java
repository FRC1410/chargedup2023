package org.frc1410.framework.scheduler.task.lock;

import org.frc1410.framework.scheduler.task.BoundTask;
import org.frc1410.framework.util.log.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class LockHandler {

    private static final Logger LOG = new Logger("LockHandler");
    private final Map<Object, @NotNull BoundTask> owners = new ConcurrentHashMap<>();

    public boolean ownsLocks(@NotNull BoundTask task) {
        if (task.lock() == null) return true;

        for (var key : task.lock().keys()) {
            var owner = owners.getOrDefault(key, task);
            if (owner == task || owner.lock() == null) {
                // Skip processing if this task owns the lock
                continue;
            }

            if (owner.lock().priority() > task.lock().priority()) {
                return false;
            }
        }


        LOG.info("Task %s owns all of its locks, transferring ownership...");

        // Take ownership
        for (var key : task.lock().keys()) {
            owners.put(key, task);
        }

        return true;
    }

    public void releaseLocks(@NotNull BoundTask task) {
        if (task.lock() == null) {
            return;
        }

        for (var key : task.lock().keys()) {
            owners.remove(key, task);
        }
    }
}