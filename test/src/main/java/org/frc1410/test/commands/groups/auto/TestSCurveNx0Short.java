package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.START;

public class TestSCurveNx0Short extends SequentialCommandGroup {
    public TestSCurveNx0Short(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(START);

        addCommands(
                Trajectories.testSCurveNx0Short(drivetrain),
                new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
                new RunCommand(() -> {})
        );
    }
}