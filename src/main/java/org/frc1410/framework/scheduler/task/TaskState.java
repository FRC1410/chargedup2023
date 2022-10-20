package org.frc1410.framework.scheduler.task;

public enum TaskState {

    REQUESTING_EXECUTION,
    EXECUTING,
    REQUESTING_CANCELLATION,
    CANCELED
}