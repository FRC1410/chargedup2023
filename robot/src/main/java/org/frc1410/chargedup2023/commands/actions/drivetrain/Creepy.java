package org.frc1410.chargedup2023.commands.actions.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import static org.frc1410.chargedup2023.util.Tuning.ENGAGE_POSITION_TOLERANCE;

public class Creepy extends CommandBase {
	private final Drivetrain drivetrain;
	private final boolean reversed;

	public Creepy(Drivetrain drivetrain, boolean reversed) {
		this.drivetrain = drivetrain;
		this.reversed = reversed;
	}

	@Override
	public void initialize() {
		if (reversed)
			drivetrain.autoTankDriveVolts(-3, -3);
		else
			drivetrain.autoTankDriveVolts(3, 3);
	}

	@Override
	public boolean isFinished() {
		return drivetrain.getPitch() > ENGAGE_POSITION_TOLERANCE || drivetrain.getPitch() < -ENGAGE_POSITION_TOLERANCE;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.autoTankDriveVolts(0, 0);
	}
}
