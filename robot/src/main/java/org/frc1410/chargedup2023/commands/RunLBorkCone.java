package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.*;

public class RunLBorkCone extends CommandBase {
	private final LBork lBork;
	private final LightBar lightBar;
	private final boolean outtaking;

	public RunLBorkCone(LBork lBork, LightBar lightBar, boolean outtaking) {
		this.lBork = lBork;
		this.lightBar = lightBar;
		this.outtaking = outtaking;

		addRequirements(lBork);
	}

	@Override
	public void initialize() {
		lightBar.set(LBork.LEDProfile.CONE);
		
		if (outtaking) {
			lBork.setRollerSpeeds(LBORK_CONE_OUTTAKE_SPEED, -LBORK_CONE_OUTTAKE_SPEED);
		} else {
			lBork.setRollerSpeeds(LBORK_CONE_INTAKE_SPEED, -LBORK_CONE_INTAKE_SPEED);
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
