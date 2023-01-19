package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.control.Controller;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

public class SwitchDriveMode extends CommandBase {
	private final Drivetrain drivetrain;
	private final Controller controller;

	public SwitchDriveMode(Drivetrain drivetrain, Controller controller) {
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