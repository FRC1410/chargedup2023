package org.frc1410.framework.scheduler.task.observer;

import org.frc1410.framework.scheduler.task.LifecycleHandle;
import org.jetbrains.annotations.NotNull;

public interface Observer {

    Observer DEFAULT = LifecycleHandle::requestExecution;

    void tick(@NotNull LifecycleHandle handle);
}