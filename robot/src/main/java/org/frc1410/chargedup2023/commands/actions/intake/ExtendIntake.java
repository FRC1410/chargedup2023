package org.frc1410.chargedup2023.commands.actions.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;


public class ExtendIntake extends CommandBase {
	private final Intake intake;

	public ExtendIntake(Intake intake) {
		this.intake = intake;

		addRequirements(this.intake);
	}

	@Override
	public void initialize() {
		intake.extend();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
