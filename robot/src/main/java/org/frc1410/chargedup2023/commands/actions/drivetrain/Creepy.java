package org.frc1410.chargedup2023.commands.actions.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.ENGAGE_POSITION_TOLERANCE;

public class Creepy extends CommandBase {
	private final Drivetrain drivetrain;
	private final boolean reversed;
	private final Timer timer = new Timer();
	private double creepyTime = CREEPY_TIME;
	public Creepy(Drivetrain drivetrain, boolean reversed) {
		this.drivetrain = drivetrain;
		this.reversed = reversed;
	}

	public Creepy(Drivetrain drivetrain, boolean reversed, double creepyTime) {
		this.drivetrain = drivetrain;
		this.reversed = reversed;
		this.creepyTime = creepyTime;
	}

	@Override
	public void initialize() {
		timer.reset();
		timer.start();
		if (reversed)
			drivetrain.autoTankDriveVolts(-CREEPY_SPEED, -CREEPY_SPEED);
		else
			drivetrain.autoTankDriveVolts(CREEPY_SPEED, CREEPY_SPEED);
	}

	@Override
	public boolean isFinished() {
		return timer.get() > creepyTime;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.autoTankDriveVolts(0, 0);
	}
}
