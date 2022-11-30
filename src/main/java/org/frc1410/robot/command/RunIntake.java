package org.frc1410.robot.command;

import org.frc1410.framework.scheduler.task.Command;
import org.frc1410.robot.subsystem.Intake;

public class RunIntake extends Command {

    private final Intake intake;

    public RunIntake(Intake intake) {
        this.intake = intake;
        dependsOn(intake);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}