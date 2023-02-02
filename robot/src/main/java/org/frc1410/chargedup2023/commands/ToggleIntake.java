package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Intake;


public class ToggleIntake extends CommandBase {
	private final Intake intake;

	public ToggleIntake(Intake intake) {
		this.intake = intake;
	}

	@Override
	public void initialize() {
		intake.toggle();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
