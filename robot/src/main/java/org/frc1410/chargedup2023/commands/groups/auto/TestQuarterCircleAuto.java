package org.frc1410.chargedup2023.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.START;

public class TestQuarterCircleAuto extends SequentialCommandGroup {
	public TestQuarterCircleAuto(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(START);
		System.out.println("Running Quarter Circle");

		addCommands(
				Trajectories.testQuarterCircle(drivetrain),
				new RunCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}
