package org.frc1410.chargedup2023.commands.actions.lbork;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;


public class ExtendLBork extends CommandBase {
	private final LBork lBork;

	public ExtendLBork(LBork lBork) {
		this.lBork = lBork;

		addRequirements(this.lBork);
	}

	@Override
	public void initialize() {
		lBork.extend();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
