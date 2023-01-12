package org.frc1410.framework.scheduler.task;

import org.jetbrains.annotations.NotNull;

public class LifecycleHandler {

    public @NotNull TaskState state = TaskState.FLAGGED_EXECUTION;

    public void requestExecution() {
        if (!state.isExecuting()) {
            state = TaskState.FLAGGED_EXECUTION;
        }
    }

    public void requestSuspension() {
        if (!state.isInactive()) {
            state = TaskState.FLAGGED_SUSPENSION;
        }
    }

    public void requestTermination() {
        if (!state.isInactive()) {
            state = TaskState.FLAGGED_TERMINATION;
        }
    }
}