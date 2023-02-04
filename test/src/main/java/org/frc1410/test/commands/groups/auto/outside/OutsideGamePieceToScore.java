package org.frc1410.test.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.OUTSIDE_GAME_PIECE_FORWARD;

public class OutsideGamePieceToScore extends SequentialCommandGroup {
	public OutsideGamePieceToScore(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(OUTSIDE_GAME_PIECE_FORWARD);

		addCommands(
				Trajectories.OutsideGamePieceToScore(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}