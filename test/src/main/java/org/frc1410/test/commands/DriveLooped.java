package org.frc1410.test.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.framework.control.Axis;

public class DriveLooped extends CommandBase {

	private final Drivetrain drivetrain;
	private final Axis leftYAxis;
	private final Axis rightYAxis;
//	private final Axis rightXAxis;
	private final Axis triggerLeft;
	private final Axis triggerRight;
	public DriveLooped(Drivetrain drivetrain, Axis leftYAxis, Axis rightYAxis, Axis triggerLeft, Axis triggerRight) {
		this.drivetrain = drivetrain;
		this.leftYAxis = leftYAxis;
		this.rightYAxis = rightYAxis;
//		this.rightXAxis = rightXAxis;
		this.triggerLeft = triggerLeft;
		this.triggerRight = triggerRight;

		addRequirements(drivetrain);
	}

	@Override
	public void execute() {
//		if (drivetrain.getDriveMode()) {
			drivetrain.triggerTankDrive(leftYAxis.get(), rightYAxis.get(), triggerRight.get(), triggerLeft.get());
//		} else {
//			drivetrain.arcadeDrive(leftYAxis.get(), rightXAxis.get(), true);
//		}
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.tankDriveVolts(0, 0);
	}
}
