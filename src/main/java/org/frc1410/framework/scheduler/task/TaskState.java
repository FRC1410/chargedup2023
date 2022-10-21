package org.frc1410.framework.scheduler.task;

public enum TaskState {

    FLAGGED_EXECUTION,
    EXECUTING,

    FLAGGED_COMPLETION,
    FLAGGED_INTERRUPTION,

    ENDED;

    public boolean isExecuting() {
        return this == EXECUTING || this == FLAGGED_EXECUTION;
    }

    public boolean isEnded() {
        return this == ENDED || this == FLAGGED_COMPLETION || this == FLAGGED_INTERRUPTION;
    }
}