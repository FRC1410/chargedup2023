package org.frc1410.chargedup2023.commands.groups.auto.barrier;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToAngle;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BARRIER_COMMUNITY_START;

public class Barrier2Cone extends SequentialCommandGroup {
	public Barrier2Cone(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_START);

		addCommands(
				Trajectories.BarrierCommunityToGamePiece(drivetrain),
				new TurnToAngle(drivetrain, 180),
				// Move forward a bit to pickup game piece
				new TurnToAngle(drivetrain, 0),
				Trajectories.BarrierGamePieceToScore(drivetrain)
		);
	}
}