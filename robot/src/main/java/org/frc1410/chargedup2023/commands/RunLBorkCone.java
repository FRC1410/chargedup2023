package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.*;

public class RunLBorkCone extends CommandBase {
    private final LBork lBork;

    private final boolean outtakeing;

    public RunLBorkCone(LBork lBork, boolean outtaeking) {
        this.lBork = lBork;
        this.outtakeing = outtaeking;
        addRequirements(this.lBork);
    }

    @Override
    public void initialize() {
        if (outtakeing) {
            lBork.setRollerSpeeds(LBORK_CONE_OUTTAKE_SPEED, -LBORK_CONE_OUTTAKE_SPEED);
        } else {
            lBork.setRollerSpeeds(LBORK_CONE_INTAKE_SPEED, -LBORK_CONE_INTAKE_SPEED);
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lBork.setRollerSpeeds(0, 0);
    }
}
