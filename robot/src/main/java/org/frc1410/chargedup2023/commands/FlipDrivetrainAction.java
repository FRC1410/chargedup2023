package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.control.Controller;
import org.frc1410.chargedup2023.subsystem.Drivetrain;


public class FlipDrivetrainAction extends CommandBase {

    private final Drivetrain drivetrain;
    private final Controller controller;
    public FlipDrivetrainAction(Drivetrain drivetrain, Controller controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements();
    }

    @Override
    public void initialize() {
        drivetrain.flip();
        controller.rumble(1, 500);
        System.out.println("Flipped drivetrain");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
