package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.START;

public class SCurveAuto extends SequentialCommandGroup {
	public SCurveAuto(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(START);

        addCommands(
            Trajectories.testSCurve(drivetrain),
            new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
        );
	}
}