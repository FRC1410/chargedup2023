package org.frc1410.framework.scheduler.task;

import org.jetbrains.annotations.NotNull;

public class LifecycleHandler {

    public final BoundTask wrappedTask;
    public @NotNull TaskState state = TaskState.FLAGGED_EXECUTION;

    public LifecycleHandler(@NotNull BoundTask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    public void requestExecution() {
        if (!state.isExecuting()) {
            state = TaskState.EXECUTING;
        }
    }

    public void requestInterruption() {
        if (!state.isInactive()) {
            state = TaskState.FLAGGED_SUSPENSION;
        }
    }
}