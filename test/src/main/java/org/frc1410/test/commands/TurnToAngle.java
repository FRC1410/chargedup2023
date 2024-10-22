package org.frc1410.test.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;


public class TurnToAngle extends CommandBase {
	private final Drivetrain drivetrain;
	private double pidOutput = 0;
	private final double kP = 0.08;
	private final double kI = 0.0035;
	private final double kD = 0.01;
	private final double targetAngle;
	private PIDController pid;

	public TurnToAngle(Drivetrain drivetrain, double targetAngle) {
		this.drivetrain = drivetrain;
		this.targetAngle = targetAngle;
	}

	@Override
	public void initialize() {
		//Create a new PID controller with the constants from networktables
		pid = new PIDController(kP, kI, kD);

		// Set the target of the controller to 0, as that is the middle of the limelight's view
		pid.setSetpoint(targetAngle);
	}

	@Override
	public void execute() {
		pidOutput = pid.calculate(drivetrain.gyro.getAngle() % 360);
		drivetrain.tankDriveVolts(-pidOutput, pidOutput);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(targetAngle - (drivetrain.gyro.getAngle() % 360)) < 1;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.tankDriveVolts(0, 0);
	}
}