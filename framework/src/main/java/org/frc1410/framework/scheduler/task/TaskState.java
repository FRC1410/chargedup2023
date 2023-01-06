package org.frc1410.framework.scheduler.task;

public enum TaskState {

    FLAGGED_EXECUTION,
    EXECUTING,

    FLAGGED_COMPLETION,
    FLAGGED_SUSPENSION,
    FLAGGED_TERMINATION,

    SUSPENDED,
    TERMINATED;

    public boolean isExecuting() {
        return this == EXECUTING || this == FLAGGED_EXECUTION;
    }

    public boolean isInactive() {
        return this == SUSPENDED || this == FLAGGED_COMPLETION || this == FLAGGED_SUSPENSION || this == FLAGGED_TERMINATION;
    }
}