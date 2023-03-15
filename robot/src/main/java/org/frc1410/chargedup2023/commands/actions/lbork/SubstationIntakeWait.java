package org.frc1410.chargedup2023.commands.actions.lbork;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.SUBSTATION_INTAKE_OFFSET_TIME;

public class SubstationIntakeWait extends CommandBase {
	private final LBork lBork;
	private final Timer timer = new Timer();
	private boolean timerRunning = false;

	public SubstationIntakeWait(LBork lBork) {
		this.lBork = lBork;
	}

	@Override
	public void execute() {
		if (lBork.getLimitSwitch() && !timerRunning) {
			timer.restart();
			timerRunning = true;
		} else if (!lBork.getLimitSwitch()) {
			timerRunning = false;
		}
	}

	@Override
	public boolean isFinished() {
		return timer.get() > SUBSTATION_INTAKE_OFFSET_TIME && timerRunning;
	}
}
