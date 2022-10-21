package org.frc1410.framework.scheduler.task;

public abstract class Observer {

    protected final LifecycleHandler lifecycle;

    public Observer(LifecycleHandler lifecycle) {
        this.lifecycle = lifecycle;
    }

    public abstract void tick();
}