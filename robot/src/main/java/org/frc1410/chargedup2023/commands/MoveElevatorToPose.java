package org.frc1410.chargedup2023.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;


public class MoveElevatorToPose extends CommandBase {
	private final Elevator elevator;
	private final double kP = 0;
	private final double kI = 0;
	private final double kD = 0;
	private PIDController pid;


	public MoveElevatorToPose(Elevator elevator) {
		this.elevator = elevator;
		addRequirements(this.elevator);
	}

	@Override
	public void initialize() {
		pid = new PIDController(kP, kI, kD);
		pid.setSetpoint(0);
		pid.setTolerance(0.1);
	}

	@Override
	public void execute() {
//		double pidOutput = pid.calculate(elevator.setDesiredState());
//		double pidOutput = pid.calculate(elevator.getEncoderPos());
//		elevator.setSpeed(pidOutput);

	}

	@Override
	public boolean isFinished() {

		return pid.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {

	}
}
