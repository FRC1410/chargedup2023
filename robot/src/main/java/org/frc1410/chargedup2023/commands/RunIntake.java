package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.framework.control.Axis;


public class RunIntake extends CommandBase {

    private final Intake intake;
    private final Axis rightTrigger;
    private final Axis leftTrigger;
    private double speed;

    public RunIntake(Intake intake, Axis leftTrigger, Axis rightTrigger) {
        this.intake = intake;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;

        addRequirements(intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        intake.setSpeed(leftTrigger.get() - rightTrigger.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setSpeed(0);
    }
}