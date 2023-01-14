package org.frc1410.test.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.*;

public class Test1MeterAuto extends SequentialCommandGroup {
    public Test1MeterAuto(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(START);
        System.out.println("Running 1 Meter");
        drivetrain.resetFollowers();

        addCommands(
                Trajectories.test1Meter(drivetrain),
                new InstantCommand(() -> System.out.println("Stopping")),
                new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
                new RunCommand(() -> {})
        );
    }
}
