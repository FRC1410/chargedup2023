package org.frc1410.chargedup2023.commands.actions;

import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleLowVoltageMode extends CommandBase {
    private final Drivetrain drivetrain;
    private final Intake intake;

    public ToggleLowVoltageMode(Drivetrain drivetrain, Intake intake) {
        this.drivetrain = drivetrain;
        this.intake = intake;
    }

    @Override
	public void initialize() {
        drivetrain.lowVoltageMode ^= true;
        intake.lowVoltageMode ^= true;
    }

    @Override
	public void execute() {

    }

    @Override
	public boolean isFinished() {
        return true;
    }

    @Override
	public void end(boolean interrupted) {}
}
