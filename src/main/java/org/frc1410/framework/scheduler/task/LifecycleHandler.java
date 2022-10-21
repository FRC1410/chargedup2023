package org.frc1410.framework.scheduler.task;

public class LifecycleHandler {

    public final BoundTask wrappedTask;

    public LifecycleHandler(BoundTask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    public void requestExecution() {

    }

    public void requestInterruption() {
        
    }
}