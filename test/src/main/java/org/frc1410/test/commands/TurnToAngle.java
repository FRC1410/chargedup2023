package org.frc1410.test.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystem.Drivetrain;

public class TurnToAngle extends CommandBase {
    private final Drivetrain drivetrain;
    private double pidOutput = 0;
    private double kP = 1/18;
    private double kI = 0;
    private double kD = 0;
    private double targetAngle;
    private PIDController pid;

    public TurnToAngle(Drivetrain drivetrain, double targetAngle) {
        this.drivetrain = drivetrain;
        this.targetAngle = targetAngle;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        //Create a new PID controller with the constants from networktables
        pid = new PIDController(kP, kI, kD);

        // Set the target of the controller to 0, as that is the middle of the limelight's view
        pid.setSetpoint(targetAngle);
        pid.enableContinuousInput(-180, 180);
    }

    @Override
    public void execute() {
        pidOutput = pid.calculate(drivetrain.gyro.getAngle() % 360);
        drivetrain.tankDriveVolts(-pidOutput, pidOutput);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDriveVolts(0, 0);
    }

    @Override
    public boolean isFinished() {
        System.out.println("Finished Turning");
        return Math.abs(targetAngle - (drivetrain.gyro.getAngle() % 360)) < 10;
    }
}