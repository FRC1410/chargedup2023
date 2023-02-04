package org.frc1410.chargedup2023.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import static org.frc1410.chargedup2023.util.Tuning.*;

public class Engage extends CommandBase {
    private final Drivetrain drivetrain;
    private final PIDController controller;

    public Engage(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        controller = new PIDController(ENGAGE_P, ENGAGE_I, ENGAGE_D);

        controller.setTolerance(ENGAGE_POSITION_TOLERANCE, ENGAGE_VELOCITY_TOLERANCE);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        controller.setSetpoint(0);
    }

    @Override
    public void execute() {
        var currentAngle = drivetrain.getPitch();
        var controllerOutput = controller.calculate(currentAngle);

//        drivetrain.setEngagePower(Math.min(controllerOutput, ENGAGE_MAX_POWER));
    }

    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDriveVolts(0, 0);
    }
}
