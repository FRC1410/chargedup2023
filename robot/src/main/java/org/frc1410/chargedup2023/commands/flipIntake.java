package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;


public class flipIntake extends CommandBase {

    private final Intake intake;

    public flipIntake(Intake intake, boolean flipped) {
        this.intake = intake;

        if(flipped) {
            intake.setFlipperReversed();
        } else if(flipped = false) {
            intake.setFlipperFoward();
        } else {
            intake.setFlipperOff();
        }
        addRequirements();
    }

    @Override
    public void initialize() {
        intake.setFlipperReversed();
    }

    @Override
    public void execute() {
        intake.toggle();
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
