package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.control.Axis;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.framework.control.Button;

public class DriveLooped extends CommandBase {

	private final Drivetrain drivetrain;
	private final Axis leftYAxis;
	private final Axis rightYAxis;
	private final Button b;

	public DriveLooped(Drivetrain drivetrain, Axis leftYAxis, Axis rightYAxis, Button b) {
		this.drivetrain = drivetrain;
		this.leftYAxis = leftYAxis;
		this.rightYAxis = rightYAxis;
		this.b = b;

		addRequirements(drivetrain);
	}

	@Override
	public void execute() {
		drivetrain.tankDrive(leftYAxis.get(), rightYAxis.get(), false);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.tankDriveVolts(0, 0);
	}
}
