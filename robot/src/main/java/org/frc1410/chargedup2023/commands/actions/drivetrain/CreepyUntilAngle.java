package org.frc1410.chargedup2023.commands.actions.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import static org.frc1410.chargedup2023.util.Constants.CREEPY_SPEED;


public class CreepyUntilAngle extends CommandBase {

	private final Drivetrain drivetrain;
	private final boolean reversed;

	public CreepyUntilAngle(Drivetrain drivetrain, boolean reversed) {
		this.drivetrain = drivetrain;
		this.reversed = reversed;

		addRequirements(drivetrain);
	}

	@Override
	public void initialize() {
		if (reversed) {
			drivetrain.autoTankDriveVolts(-CREEPY_SPEED, -CREEPY_SPEED);
		} else {
			drivetrain.autoTankDriveVolts(CREEPY_SPEED, CREEPY_SPEED);
		}
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return Math.abs(drivetrain.getPitch()) > 13; // Degrees
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.autoTankDriveVolts(0, 0);
		System.out.println("Creepy until angle command ending");
	}
}
