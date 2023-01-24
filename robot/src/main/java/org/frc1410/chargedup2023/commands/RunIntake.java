package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;


public class RunIntake extends CommandBase {

    private final Intake intake;
    private final double speed;

    public RunIntake(Intake intake, double speed) {
        this.intake = intake;
        this.speed = speed;

        addRequirements(intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        intake.setIntakeSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntakeSpeed(0);
    }
}
