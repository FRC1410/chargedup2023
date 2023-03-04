package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.framework.control.Axis;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.*;


public class RunIntakeLooped extends CommandBase {
	private final Elevator elevator;
	private final Intake intake;
	private final LBork lBork;
	private final TaskScheduler scheduler;
	private final Axis rightTrigger;
	private final Axis leftTrigger;

	public RunIntakeLooped(Elevator elevator, Intake intake, LBork lBork, TaskScheduler scheduler, Axis leftTrigger, Axis rightTrigger) {
		this.elevator = elevator;
		this.intake = intake;
		this.lBork = lBork;
		this.scheduler = scheduler;
		this.leftTrigger = leftTrigger;
		this.rightTrigger = rightTrigger;

		addRequirements(intake);
	}

	@Override
	public void execute() {
		if (!intake.isRetracted()) {
			intake.setSpeed(leftTrigger.get() - rightTrigger.get());
		} else {
			intake.setSpeed(0);
		}

		if (leftTrigger.get() > 0 || rightTrigger.get() > 0) {
			scheduler.scheduleAutoCommand(new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_PAPA_POSITION, true, false));
		}

		if (rightTrigger.get() > leftTrigger.get()) {
			lBork.setRollerSpeeds(LBORK_PAPA_INTAKE_OUTER_ROLLER_SPEED, LBORK_PAPA_INTAKE_INNER_ROLLER_SPEED);
		} else if (rightTrigger.get() < leftTrigger.get()) {
			lBork.setRollerSpeeds(LBORK_PAPA_OUTTAKE_OUTER_ROLLER_SPEED, LBORK_PAPA_OUTTAKE_INNER_ROLLER_SPEED);
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
