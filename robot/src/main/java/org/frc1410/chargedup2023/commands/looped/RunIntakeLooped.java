package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;
import org.frc1410.framework.control.Axis;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.*;


public class RunIntakeLooped extends CommandBase {
	private final Intake intake;
	private final LBork lBork;
	private final LightBar lightBar;
	private final Axis rightTrigger;
	private final Axis leftTrigger;

	public RunIntakeLooped(Intake intake, LBork lBork, LightBar lightBar, Axis leftTrigger, Axis rightTrigger) {
		this.intake = intake;
		this.lBork = lBork;
		this.lightBar = lightBar;
		this.leftTrigger = leftTrigger;
		this.rightTrigger = rightTrigger;

		addRequirements(intake, lBork);
	}

	@Override
	public void execute() {
		if (lBork.getLimitSwitch()) lightBar.set(LightBar.Profile.IDLE_PIECE);
		else lightBar.set(LightBar.Profile.IDLE_NO_PIECE);

		intake.setSpeed(0.8 * (leftTrigger.get() - rightTrigger.get()));

		if (rightTrigger.get() > leftTrigger.get()) {
			lBork.setRollerSpeeds(-LBORK_PAPA_INTAKE_OUTER_ROLLER_SPEED, -LBORK_PAPA_INTAKE_INNER_ROLLER_SPEED);
		} else if (rightTrigger.get() < leftTrigger.get()) {
			lBork.setRollerSpeeds(-LBORK_PAPA_OUTTAKE_OUTER_ROLLER_SPEED, -LBORK_PAPA_OUTTAKE_INNER_ROLLER_SPEED);
		} else {
			lBork.setRollerSpeeds(0, 0);
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.setSpeed(0);
		lBork.setRollerSpeeds(0, 0);
	}
}
