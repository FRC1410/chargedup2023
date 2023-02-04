package org.frc1410.chargedup2023.commands.actions.lbork;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;


public class RetractLBork extends CommandBase {
	private final LBork lBork;

	public RetractLBork(LBork lBork) {
		this.lBork = lBork;

		addRequirements(this.lBork);
	}

	@Override
	public void initialize() {
		lBork.retract();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
