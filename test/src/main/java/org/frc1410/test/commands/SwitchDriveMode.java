package org.frc1410.test.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.framework.control.Controller;

public class SwitchDriveMode extends CommandBase {
	private final Drivetrain drivetrain;
	private final Controller controller;

	public SwitchDriveMode(Drivetrain drivetrain, Controller controller) {
		this.drivetrain = drivetrain;
		this.controller = controller;
		addRequirements();
	}

	@Override
	public void initialize() {
		drivetrain.switchDriveMode();
		controller.rumble(1, 500);
		System.out.println("Switching drive mode");
	}

	@Override
	public boolean isFinished() {return true;}
}