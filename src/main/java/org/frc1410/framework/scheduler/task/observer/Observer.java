package org.frc1410.framework.scheduler.task.observer;

import org.frc1410.framework.scheduler.task.LifecycleHandler;

public interface Observer {

    void tick(LifecycleHandler lifecycle);
}