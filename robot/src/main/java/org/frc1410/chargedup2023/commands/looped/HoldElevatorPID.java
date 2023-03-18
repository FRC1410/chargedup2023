package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_TOLERANCE;
import static org.frc1410.chargedup2023.util.Tuning.*;

public class HoldElevatorPID extends CommandBase {
	private final Elevator elevator;
	private PIDController pid;

	public HoldElevatorPID(Elevator elevator) {
		this.elevator = elevator;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		pid = new PIDController(ELEVATOR_HOLD_KP, ELEVATOR_HOLD_KI, ELEVATOR_HOLD_KD);
		pid.setTolerance(ELEVATOR_TOLERANCE);

		pid.setSetpoint(elevator.getDesiredPosition());
	}

	@Override
	public void execute() {
		pid.setSetpoint(elevator.getDesiredPosition());
		if (elevator.getPosition() > 1.5) elevator.setVolts(-(pid.calculate(elevator.getPosition()) + 0.24));
		else elevator.setVolts(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setVolts(0);
	}
}
