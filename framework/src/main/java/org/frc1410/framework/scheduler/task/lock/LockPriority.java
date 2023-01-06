package org.frc1410.framework.scheduler.task.lock;

public interface LockPriority {

    int NULL = -1;

    int LOWEST = 0;
    int LOW = 1;
    int NORMAL = 2;
    int HIGH = 3;
    int HIGHEST = 4;
}