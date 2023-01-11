package org.frc1410.chargedup2023.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

public class Test90DegreeAuto extends SequentialCommandGroup {
    public Test90DegreeAuto(Drivetrain drivetrain) {
        addCommands(
//                Trajectories.test90Degrees(drivetrain),
                new RunCommand(() -> drivetrain.tankDriveVolts(0, 0))
        );
    }
}
