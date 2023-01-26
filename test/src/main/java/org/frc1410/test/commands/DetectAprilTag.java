package org.frc1410.test.commands;

import org.frc1410.framework.control.Controller;
import org.frc1410.test.subsystems.ExternalCamera;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DetectAprilTag extends CommandBase {
    private final ExternalCamera camera;
    private final Controller controller;

    public DetectAprilTag(ExternalCamera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }

    @Override
    public void execute() {
        if (camera.hasTargets()) {
            controller.rumble(1000);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        controller.rumble(0);
    }
}