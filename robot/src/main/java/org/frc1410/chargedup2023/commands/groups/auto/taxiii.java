package org.frc1410.chargedup2023.commands.groups.auto;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

public class taxiii extends SequentialCommandGroup {
	public taxiii(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(new Pose2d(0, 0, Rotation2d.fromDegrees(0)));

		addCommands(
				Trajectories.Taxiiii(drivetrain)
		);
	}
}