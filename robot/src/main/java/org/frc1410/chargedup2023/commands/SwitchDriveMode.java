package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetraininator;
import org.frc1410.framework.control.Controller;

public class SwitchDriveMode extends CommandBase {
	private final Drivetraininator drivetrain;
	private final Controller controller;

	public SwitchDriveMode(Drivetraininator drivetrain, Controller controller) {
		this.drivetrain = drivetrain;
		this.controller = controller;
	}

	@Override
	public void initialize() {
		drivetrain.switchDriveMode();
		controller.rumble(1, 500);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}