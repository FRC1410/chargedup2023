package org.frc1410.test.commands;

import org.frc1410.framework.control.Controller;
import org.frc1410.test.subsystems.ExternalCamera;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DetectAprilTag extends CommandBase {

	private final ExternalCamera camera;

	private final Controller controller;

	private boolean foundTarget = false;

	public DetectAprilTag(ExternalCamera camera, Controller controller) {
		this.camera = camera;
		this.controller = controller;
	}

	@Override
	public void initialize() {
		this.foundTarget = false;
	}

	@Override
	public void execute() {
		var foundTarget = camera.hasTargets();

		if (foundTarget && !this.foundTarget) {
			controller.rumble(1);
		} else if (!foundTarget && this.foundTarget) {
			controller.rumble(0);
		}

		this.foundTarget = foundTarget;
	}

	@Override
	public void end(boolean interrupted) {
		controller.rumble(0);
	}
}


