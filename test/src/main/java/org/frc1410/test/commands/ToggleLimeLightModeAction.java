package org.frc1410.test.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystem.Limelight;

public class ToggleLimeLightModeAction extends CommandBase {

    private final Limelight limeLight;

    public ToggleLimeLightModeAction(Limelight limeLight) {
        this.limeLight = limeLight;

        addRequirements(limeLight);
    }

    @Override
    public void initialize() {
        limeLight.toggleMode();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
