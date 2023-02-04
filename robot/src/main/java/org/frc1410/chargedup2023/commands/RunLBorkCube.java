package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.*;

public class RunLBorkCube extends CommandBase {
	private final LBork lBork;
	private final boolean outtaking;

	public RunLBorkCube(LBork lBork, boolean outtaking) {
		this.lBork = lBork;
		this.outtaking = outtaking;

		addRequirements(lBork);
	}

	@Override
	public void initialize() {
		if (outtaking) {
			lBork.setRollerSpeeds(LBORK_CUBE_OUTTAKE_SPEED, LBORK_CUBE_OUTTAKE_SPEED);
		} else {
			lBork.setRollerSpeeds(LBORK_CUBE_INTAKE_SPEED, LBORK_CUBE_INTAKE_SPEED);
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
