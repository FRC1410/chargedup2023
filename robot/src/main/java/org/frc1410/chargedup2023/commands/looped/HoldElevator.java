package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_HOLDING_POWER;


public class HoldElevator extends CommandBase {
	private final Elevator elevator;

	public HoldElevator(Elevator elevator) {
		this.elevator = elevator;

		addRequirements(this.elevator);
	}

	@Override
	public void execute() {
		if (elevator.getPosition() < 1.5) {
			elevator.setSpeed(ELEVATOR_HOLDING_POWER);
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setSpeed(0);
	}
}
