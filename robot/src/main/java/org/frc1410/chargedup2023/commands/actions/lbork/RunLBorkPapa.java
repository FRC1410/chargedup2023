package org.frc1410.chargedup2023.commands.actions.lbork;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.*;

public class RunLBorkPapa extends CommandBase {
	private final LBork lBork;
	private final boolean outtaking;

	public RunLBorkPapa(LBork lBork, boolean outtaking) {
		this.lBork = lBork;
		this.outtaking = outtaking;

		addRequirements(lBork);
	}

	@Override
	public void initialize() {
		if (outtaking) {
			lBork.setRollerSpeeds(LBORK_PAPA_OUTTAKE_OUTER_ROLLER_SPEED, LBORK_PAPA_OUTTAKE_INNER_ROLLER_SPEED);
		} else {
			lBork.setRollerSpeeds(LBORK_PAPA_INTAKE_OUTER_ROLLER_SPEED, LBORK_PAPA_INTAKE_INNER_ROLLER_SPEED);
		}
	}

	@Override
	public void end(boolean interrupted) {
		lBork.setRollerSpeeds(0, 0);
	}
}
