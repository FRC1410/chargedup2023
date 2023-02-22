package org.frc1410.chargedup2023.commands.actions.elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;


public class MoveElevatorToPose extends CommandBase {

	private final Elevator elevator;
	private final double targetPosition;
	private PIDController pid;


	public MoveElevatorToPose(Elevator elevator, double encoderPosition) {
		this.elevator = elevator;
		this.targetPosition = encoderPosition;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		pid = new PIDController(ELEVATOR_KP, ELEVATOR_KI, ELEVATOR_KD);

		pid.setSetpoint(targetPosition);

		pid.setTolerance(ELEVATOR_TOLERANCE);
	}

	@Override
	public void execute() {
		elevator.setVolts(Math.min(pid.calculate(elevator.getEncoderValue()), ELEVATOR_MAX_OUTPUT));
	}

	@Override
	public boolean isFinished() {
		return pid.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setSpeed(0);
	}
}
