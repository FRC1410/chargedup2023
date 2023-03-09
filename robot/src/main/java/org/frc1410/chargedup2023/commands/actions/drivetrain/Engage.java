package org.frc1410.chargedup2023.commands.actions.drivetrain;

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

		if (currentAngle > ENGAGE_POSITION_TOLERANCE || currentAngle < -ENGAGE_POSITION_TOLERANCE) {
			System.out.println("Running");
			var controllerOutput = controller.calculate(currentAngle);

			// If somebody changes this they will be skinned alive
			drivetrain.setEngagePower(-(Math.log1p(Math.min(controllerOutput, ENGAGE_MAX_POWER)))/Math.log1p(4));
		} else {
			drivetrain.autoTankDriveVolts(0, 0);
		}
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.autoTankDriveVolts(0, 0);
    }
}
