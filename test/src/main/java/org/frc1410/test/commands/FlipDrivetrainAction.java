package org.frc1410.test.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.framework.control.Controller;


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
		controller.rumble(500L);
		System.out.println("Flipped drivetrain");
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
