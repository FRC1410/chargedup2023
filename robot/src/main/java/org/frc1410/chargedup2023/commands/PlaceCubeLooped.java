package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;


public class PlaceCubeLooped extends CommandBase {

    private final LBork lBork;
    public PlaceCubeLooped(LBork lBork) {
        this.lBork = lBork;
        addRequirements(lBork);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        lBork.placeCube();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
