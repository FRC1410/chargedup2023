package org.frc1410.test.commands.groups.auto.barrier;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.BARRIER_GAME_PIECE_FORWARD;

public class BarrierGamePieceToScore extends SequentialCommandGroup {
	public BarrierGamePieceToScore(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_GAME_PIECE_FORWARD);

		addCommands(
				Trajectories.BarrierGamePieceToScore(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0 ,0))
		);
	}
}