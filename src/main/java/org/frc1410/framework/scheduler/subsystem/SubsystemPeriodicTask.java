package org.frc1410.framework.scheduler.subsystem;

import org.frc1410.framework.scheduler.task.Task;

public class SubsystemPeriodicTask implements Task {

    private final TickedSubsystem subsystem;

    public SubsystemPeriodicTask(TickedSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() {
        subsystem.periodic();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}