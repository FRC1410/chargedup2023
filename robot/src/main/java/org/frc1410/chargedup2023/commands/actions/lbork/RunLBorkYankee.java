package org.frc1410.chargedup2023.commands.actions.lbork;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.*;

public class RunLBorkYankee extends CommandBase {
	private final LBork lBork;
	private final boolean outtaking;

	public RunLBorkYankee(LBork lBork, boolean outtaking) {
		this.lBork = lBork;
		this.outtaking = outtaking;

		addRequirements(lBork);
	}

	@Override
	public void initialize() {
		System.out.println("Running");
		if (outtaking) {
			lBork.setRollerSpeeds(-LBORK_YANKEE_OUTTAKE_SPEED, LBORK_YANKEE_OUTTAKE_SPEED);
		} else {
			lBork.setRollerSpeeds(LBORK_YANKEE_INTAKE_OUTER_ROLLER_SPEED, LBORK_YANKEE_INTAKE_INNER_ROLLER_SPEED);
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		lBork.setRollerSpeeds(0, 0);
	}
}
