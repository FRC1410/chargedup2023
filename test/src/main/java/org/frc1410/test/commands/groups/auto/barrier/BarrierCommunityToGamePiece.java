package org.frc1410.test.commands.groups.auto.barrier;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.commands.TurnToAngle;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.BARRIER_COMMUNITY_START;

public class BarrierCommunityToGamePiece extends SequentialCommandGroup {
	
	public BarrierCommunityToGamePiece(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_START);

		addCommands(
				Trajectories.BarrierCommunityToGamePiece(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
				new TurnToAngle(drivetrain, 180),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}