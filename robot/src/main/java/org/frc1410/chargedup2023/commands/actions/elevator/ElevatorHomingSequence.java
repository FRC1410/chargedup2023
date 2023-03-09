package org.frc1410.chargedup2023.commands.actions.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_HOMING_SPEED;

public class ElevatorHomingSequence extends CommandBase {
	private final Elevator elevator;

	public ElevatorHomingSequence(Elevator elevator) {
		this.elevator = elevator;

		addRequirements(elevator);
	}

	@Override
	public void execute() {
		elevator.setSpeed(ELEVATOR_HOMING_SPEED);
	}

	@Override
	public boolean isFinished() {
		return elevator.getLimitSwitchValue();
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setSpeed(0);
		elevator.resetPosition(0);
		elevator.setDesired_position(0);
	}
}
