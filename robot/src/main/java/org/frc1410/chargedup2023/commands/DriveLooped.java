package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.framework.control.Axis;

public class DriveLooped extends CommandBase {

    private final Drivetrain drivetrain;
    private final Axis leftYAxis;
    private final Axis rightYAxis;
    private final Axis rightXAxis;
    public DriveLooped(Drivetrain drivetrain, Axis leftYAxis, Axis rightYAxis, Axis rightXAxis) {
        this.drivetrain = drivetrain;
        this.leftYAxis = leftYAxis;
        this.rightYAxis = rightYAxis;
        this.rightXAxis = rightXAxis;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if (drivetrain.getDriveMode()) {
            drivetrain.arcadeDrive(leftYAxis.get(), rightXAxis.get(), false);
        } else {
            drivetrain.tankDrive(leftYAxis.get(), rightYAxis.get(), false);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDriveVolts(0, 0);
    }
}
