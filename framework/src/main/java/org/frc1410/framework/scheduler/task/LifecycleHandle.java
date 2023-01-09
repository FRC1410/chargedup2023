package org.frc1410.framework.scheduler.task;

import org.jetbrains.annotations.NotNull;

public class LifecycleHandle {

    public @NotNull TaskState state = TaskState.FLAGGED_EXECUTION;

    public void requestExecution() {
        if (!state.isExecuting()) {
            state = TaskState.FLAGGED_EXECUTION;
        }
    }

    public void requestInterruption() {
        if (!state.isInactive()) {
            state = TaskState.FLAGGED_SUSPENSION;
        }
    }
}