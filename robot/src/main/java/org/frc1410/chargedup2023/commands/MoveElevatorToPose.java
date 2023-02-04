package org.frc1410.chargedup2023.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;


public class MoveElevatorToPose extends CommandBase {
	private final Elevator elevator;
	private final Elevator.State state;
	private double pidOutput = 0;
	private PIDController pid;


	public MoveElevatorToPose(Elevator elevator, Elevator.State state) {
		this.elevator = elevator;
		this.state = state;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {
		pid = new PIDController(ELEVATOR_KP, ELEVATOR_KI, ELEVATOR_KD);

		switch(state) {
			case DOWN -> pid.setSetpoint(ELEVATOR_DOWN_POSITION);
			case STAGE_1 -> pid.setSetpoint(ELEVATOR_STAGE_ONE_POSITION);
			case STAGE_2 -> pid.setSetpoint(ELEVATOR_STAGE_TWO_POSITION);
			case RAISED -> pid.setSetpoint(ELEVATOR_RAISED_POSITION);
		}

		pid.setTolerance(ELEVATOR_TOLERANCE);
	}

	@Override
	public void execute() {
		pidOutput = pid.calculate(elevator.encoderValue());
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
