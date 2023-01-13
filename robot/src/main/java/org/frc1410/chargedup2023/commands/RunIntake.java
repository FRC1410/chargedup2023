package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystem.Intake;
import org.frc1410.framework.control.Axis;


public class RunIntake extends CommandBase {

    private final Intake intake;
    private final Axis axis;

    public RunIntake(Intake intake, Axis axis) {
        this.intake = intake;
        this.axis = axis;


        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        intake.setIntakeSpeed(axis.get());
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntakeSpeed(0);
    }
}
