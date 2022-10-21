package org.frc1410.framework.scheduler.task;

public class LifecycleHandler {

    public final BoundTask wrappedTask;
    public TaskState state = TaskState.FLAGGED_EXECUTION;

    public LifecycleHandler(BoundTask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    public void requestExecution() {
        if (!state.isExecuting()) {
            state = TaskState.EXECUTING;
        }
    }

    public void requestInterruption() {
        if (!state.isEnded()) {
            state = TaskState.FLAGGED_INTERRUPTION;
        }
    }
}