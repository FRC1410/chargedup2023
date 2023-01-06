package org.frc1410.framework.scheduler.task.observer;

import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.jetbrains.annotations.NotNull;

public interface Observer {

    Observer DEFAULT = LifecycleHandler::requestExecution;

    void tick(@NotNull LifecycleHandler lifecycle);
}