package org.frc1410.chargedup2023.commands.groups.auto.barrier;


import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;


import static org.frc1410.chargedup2023.auto.POIs.BARRIER_COMMUNITY_START;

public class Barrier2ConeCube extends SequentialCommandGroup {
	public Barrier2ConeCube(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_START);

		addCommands(
				new WaitCommand(0.5),
				Trajectories.BarrierCommunityToGrid(drivetrain),
				new WaitCommand(0.7),
				Trajectories.BarrierGridToGamePiece(drivetrain),
				new TurnToSmallAngle(drivetrain, 180),
				Trajectories.BarrierGamePieceToIntake(drivetrain),
				new TurnToSmallAngle(drivetrain, 0),
				Trajectories.BarrierGamePieceToScoreNuclear(drivetrain),
				new WaitCommand(0.7),
				Trajectories.BarrierScoreToMiddleGamePiece(drivetrain),
				new TurnToSmallAngle(drivetrain, -48+180),
				Trajectories.BarrierMiddleGamePieceToIntake(drivetrain),
				new TurnToSmallAngle(drivetrain, -48),
				Trajectories.BarrierMiddleGamePieceToScoreCube(drivetrain),
				new RunCommand(() -> {})
		);
	}
}