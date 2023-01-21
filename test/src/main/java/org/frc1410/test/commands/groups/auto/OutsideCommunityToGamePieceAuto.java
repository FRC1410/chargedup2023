package org.frc1410.test.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.commands.TurnToAngle;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.OUTSIDE_COMMUNITY_START;

public class OutsideCommunityToGamePieceAuto extends SequentialCommandGroup {
	public OutsideCommunityToGamePieceAuto(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(OUTSIDE_COMMUNITY_START);

		addCommands(
//			Trajectories.OutsideCommunityToMidpoint(drivetrain),
//			Trajectories.OutsideMidpointToGamePiece(drivetrain),
//			Trajectories.OutsideCommunityToGamePiece(drivetrain),
			new TurnToAngle(drivetrain, 180),
			new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}
