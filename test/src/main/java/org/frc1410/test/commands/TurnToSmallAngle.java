package org.frc1410.test.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;


public class TurnToSmallAngle extends CommandBase {
    private final Drivetrain drivetrain;
    private double pidOutput = 0;
    private double kP = 0.15;
    private double kI = 0; // 0.0035
    private double kD = 0.01; // 0.01
    private double targetAngle;
    private PIDController pid;

    public TurnToSmallAngle(Drivetrain drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;
        this.targetAngle = targetAngle;

//        addRequirements(drivetrain);
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
        return Math.abs(targetAngle - drivetrain.getHeading()) < 0.5;
	}

	@Override
	public void end(boolean interrupted) {
        drivetrain.tankDriveVolts(0, 0);
	}
}