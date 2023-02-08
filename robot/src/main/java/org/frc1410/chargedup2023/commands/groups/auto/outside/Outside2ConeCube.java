package org.frc1410.chargedup2023.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.OUTSIDE_COMMUNITY_START;

public class Outside2ConeCube extends SequentialCommandGroup {
	public Outside2ConeCube(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(OUTSIDE_COMMUNITY_START);

		addCommands(
				new WaitCommand(0.5),
				Trajectories.OutsideCommunityToGrid(drivetrain),
				new WaitCommand(0.7),
				Trajectories.OutsideGridToGamePiece(drivetrain),
				new TurnToSmallAngle(drivetrain, 180),
				Trajectories.OutsideGamePieceToIntake(drivetrain),
				new TurnToSmallAngle(drivetrain, 0),
				Trajectories.OutsideGamePieceToScoreNuclear(drivetrain),
				new WaitCommand(0.7),
				Trajectories.OutsideScoreToMiddleGamePiece(drivetrain),
				new TurnToSmallAngle(drivetrain, 48-180),
				Trajectories.OutsideMiddleGamePieceToIntake(drivetrain),
				new TurnToSmallAngle(drivetrain, 48),
				Trajectories.OutsideMiddleGamePieceToScoreCube(drivetrain),
				new RunCommand(() -> {})
		);
	}
}