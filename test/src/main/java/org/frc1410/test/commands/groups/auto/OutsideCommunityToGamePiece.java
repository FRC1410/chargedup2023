package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.commands.TurnToAngle;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.OUTSIDE_COMMUNITY_START;

public class OutsideCommunityToGamePiece extends SequentialCommandGroup {
	
	public OutsideCommunityToGamePiece(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(OUTSIDE_COMMUNITY_START);

		addCommands(
				Trajectories.OutsideCommunityToGamePiece(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
				new TurnToAngle(drivetrain, 180),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}