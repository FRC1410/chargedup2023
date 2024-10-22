package org.frc1410.chargedup2023.commands.actions.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;

public class RunIntake extends CommandBase {
	private final Intake intake;
	private final boolean outtake;

	public RunIntake(Intake intake, boolean outtake) {
		this.intake = intake;
		this.outtake = outtake;
		addRequirements(intake);
	}

	@Override
	public void initialize() {
		if (outtake) intake.setSpeed(1);
		else intake.setSpeed(-0.3);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.setSpeed(0);
	}
}
