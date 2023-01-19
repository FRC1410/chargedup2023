package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.START;

public class TestSCurve1x1Long extends SequentialCommandGroup {
	public TestSCurve1x1Long(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(START);

        addCommands(
            Trajectories.testSCurve1x1Long(drivetrain),
            new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
			new RunCommand(() -> {})
        );
	}
}