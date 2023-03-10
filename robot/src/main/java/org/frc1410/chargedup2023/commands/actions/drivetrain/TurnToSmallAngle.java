package org.frc1410.chargedup2023.commands.actions.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;


public class TurnToSmallAngle extends CommandBase {
    private final Drivetrain drivetrain;
    private double pidOutput = 0;
    private final double kP = 0.13;
    private final double kI = 0; // 0.0035
    private final double kD = 0.01; // 0.01
    private final double targetAngle;
    private PIDController pid;

    public TurnToSmallAngle(Drivetrain drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;
        this.targetAngle = targetAngle;
    }

	@Override
	public void initialize() {
        //Create a new PID controller with the constants from networktables
        pid = new PIDController(kP, kI, kD);

        // Set the target of the controller to 0, as that is the middle of the limelight's view
		pid.setSetpoint(targetAngle);

		pid.setTolerance(2, 0.5);
	}

	@Override
	public void execute() {
		double angle;
		if (targetAngle == 180) {
			angle = drivetrain.getPoseEstimation().getRotation().getDegrees() >= 0
					? drivetrain.getPoseEstimation().getRotation().getDegrees()
					: drivetrain.getPoseEstimation().getRotation().getDegrees() + 360;
		} else {
			angle = drivetrain.getPoseEstimation().getRotation().getDegrees();
		}

        pidOutput = pid.calculate(angle);
        drivetrain.autoTankDriveVolts(-pidOutput, pidOutput);
	}

	@Override
	public boolean isFinished() {
        return pid.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
        drivetrain.autoTankDriveVolts(0, 0);
	}
}