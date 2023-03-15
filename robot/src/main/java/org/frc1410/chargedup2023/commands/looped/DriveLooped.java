package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.control.Axis;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

public class DriveLooped extends CommandBase {

	private final Drivetrain drivetrain;
	private final Axis leftYAxis;
	private final Axis rightYAxis;
	private final Axis triggerLeft;
	private final Axis triggerRight;
	
	public DriveLooped(Drivetrain drivetrain, Axis leftYAxis, Axis rightYAxis, Axis triggerLeft, Axis triggerRight) {
		this.drivetrain = drivetrain;
		this.leftYAxis = leftYAxis;
		this.rightYAxis = rightYAxis;
		this.triggerLeft = triggerLeft;
		this.triggerRight = triggerRight;

		addRequirements(drivetrain);
	}

	@Override
	public void execute() {
//		drivetrain.triggerTankDrive(leftYAxis.get(), rightYAxis.get(), triggerRight.get(), triggerLeft.get());
		drivetrain.adaptiveTankDrive(leftYAxis.get(), rightYAxis.get(), triggerRight.get(), triggerLeft.get());
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.autoTankDriveVolts(0, 0);
	}
}
