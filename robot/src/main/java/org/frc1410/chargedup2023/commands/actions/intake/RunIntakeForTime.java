package org.frc1410.chargedup2023.commands.actions.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;


public class RunIntakeForTime extends CommandBase {
	private final Intake intake;

	private final double speed;

	private final double time;

	private final Timer timer = new Timer();

	public RunIntakeForTime(Intake intake, double speed, double time) {
		this.intake = intake;
		this.speed = speed;
		this.time = time;

		addRequirements(this.intake);
	}

	@Override
	public void initialize() {
		timer.reset();
	}

	@Override
	public void execute() {
		intake.setSpeed(speed);
	}

	@Override
	public boolean isFinished() {
		return timer.get() >= time;
	}

	@Override
	public void end(boolean interrupted) {
		intake.setSpeed(0);
	}
}
