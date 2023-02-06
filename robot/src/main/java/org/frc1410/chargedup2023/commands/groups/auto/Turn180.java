package org.frc1410.chargedup2023.commands.groups.auto;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.TurnToAngle;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

public class Turn180 extends SequentialCommandGroup {
	public Turn180(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(new Pose2d(1, 1, new Rotation2d(0)));

		addCommands(
				new TurnToAngle(drivetrain, 180),
				new RunCommand(() -> {
					drivetrain.tankDriveVolts(0, 0);
					drivetrain.resetPoseEstimation(new Pose2d(1, 1, new Rotation2d(0)));
				})
		);
	}
}