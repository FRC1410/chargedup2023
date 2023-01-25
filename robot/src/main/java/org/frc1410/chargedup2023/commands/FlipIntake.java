package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.framework.control.Controller;


public class FlipIntake extends CommandBase {

    private final Intake intake;
    private final Controller controller;

    public FlipIntake(Intake intake, Controller controller) {
        this.intake = intake;
        this.controller = controller;

        addRequirements();
    }

    @Override
    public void initialize() {
        intake.toggle();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
