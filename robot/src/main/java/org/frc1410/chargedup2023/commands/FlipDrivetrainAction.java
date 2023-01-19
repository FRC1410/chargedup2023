package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetraininator;
import org.frc1410.framework.control.Controller;

public class FlipDrivetrainAction extends CommandBase {

    private final Drivetraininator drivetrain;
    private final Controller controller;
    
    public FlipDrivetrainAction(Drivetraininator drivetrain, Controller controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements();
    }

    @Override
    public void initialize() {
        drivetrain.flip();
        controller.rumble(1, 500);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
