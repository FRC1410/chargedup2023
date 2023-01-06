package org.frc1410.framework.scheduler.subsystem;

public interface TickedSubsystem extends Subsystem {

    default long getPeriod() {
        return -1L;
    }

    void periodic();
}