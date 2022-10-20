package org.frc1410.framework.scheduler.task;

public class LifecycleHandler {

    public final BoundTask wrappedTask;

    private TaskState state;

    public LifecycleHandler(BoundTask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    public TaskState getState() {
        return state;
    }

    public void flagExecution() {
        state = TaskState.FLAGGED_EXECUTION;
    }

    public void flagCancellation() {
        state = TaskState.FLAGGED_CANCELLATION;
    }
}