package org.frc1410.chargedup2023.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.*;

public class Test2MeterAuto extends SequentialCommandGroup {
    public Test2MeterAuto(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(START);
        System.out.println("Running 2 Meter");

        addCommands(
                Trajectories.test2Meter(drivetrain),
                new RunCommand(() -> drivetrain.tankDriveVolts(0, 0))
        );
    }
}
