package org.frc1410.framework.scheduler.task;

public enum TaskState {

    FLAGGED_EXECUTION,
    EXECUTING,

    FLAGGED_COMPLETION,
    FLAGGED_SUSPENSION,
    SUSPENDED,

    FLAGGED_TERMINATION,
    TERMINATED;

    public boolean isExecuting() {
        return this == EXECUTING || this == FLAGGED_EXECUTION;
    }

    public boolean isInactive() {
        return !isExecuting();
    }

    public boolean isTerminated() {
        return this == FLAGGED_TERMINATION || this == TERMINATED;
    }
}