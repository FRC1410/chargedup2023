package org.frc1410.chargedup2023.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

public class TurnToAngle extends CommandBase {
	
	private final Drivetrain drivetrain;
	private double pidOutput = 0;
	private final double kP = 0.06;
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
//		System.out.println("pidOutput = " + pidOutput);
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.tankDriveVolts(0, 0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(targetAngle - drivetrain.getHeading()) < 1;
	}
}