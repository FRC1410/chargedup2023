package org.frc1410.framework.scheduler;

public interface Task {

    void init();

    void execute();

    boolean isFinished();

    void end(boolean interrupted);
}