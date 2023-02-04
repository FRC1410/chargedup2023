package org.frc1410.chargedup2023.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;


public class MoveElevatorToPose extends CommandBase {
	public enum State {
		DOWN,
		DRIVING,
		CUBE,
		MID,
		RAISED;
	}
	private final Elevator elevator;
	private final State state;
	private double pidOutput = 0;
	private PIDController pid;


	public MoveElevatorToPose(Elevator elevator, State state) {
		this.elevator = elevator;
		this.state = state;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		pid = new PIDController(ELEVATOR_KP, ELEVATOR_KI, ELEVATOR_KD);

		switch(state) {
			case DOWN -> pid.setSetpoint(ELEVATOR_DOWN_POSITION);
			case DRIVING -> pid.setSetpoint(ELEVATOR_DRIVING_POSITION);
			case CUBE -> pid.setSetpoint(ELEVATOR_CUBE_POSITION);
			case MID -> pid.setSetpoint(ELEVATOR_MID_POSITION);
			case RAISED -> pid.setSetpoint(ELEVATOR_RAISED_POSITION);
		}

		pid.setTolerance(ELEVATOR_TOLERANCE);
	}

	@Override
	public void execute() {
		pidOutput = pid.calculate(elevator.getEncoderValue());
		elevator.setSpeed(pidOutput);
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
