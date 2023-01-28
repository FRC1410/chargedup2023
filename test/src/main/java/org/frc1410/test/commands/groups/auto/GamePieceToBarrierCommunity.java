package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.commands.TurnToAngle;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.*;

public class GamePieceToBarrierCommunity extends SequentialCommandGroup {
	
	public GamePieceToBarrierCommunity(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_GAME_PIECE_BACKWARD);

		addCommands(
				Trajectories.BarrierGamePieceToCommunity(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0 ,0)),
				new TurnToAngle(drivetrain, 0)
		);
	}
}