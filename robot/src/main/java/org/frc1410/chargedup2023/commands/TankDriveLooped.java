package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.framework.control2.Axis;

public class TankDriveLooped extends CommandBase {

    private final Drivetrain drivetrain;
    private final Axis leftAxis;
    private final Axis rightAxis;
    public TankDriveLooped(Drivetrain drivetrain, Axis leftAxis, Axis rightAxis) {
        this.drivetrain = drivetrain;
        this.leftAxis = leftAxis;
        this.rightAxis = rightAxis;
    }

    @Override
    public void execute() {
        drivetrain.tankDrive(leftAxis.get(), rightAxis.get(), false);
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
