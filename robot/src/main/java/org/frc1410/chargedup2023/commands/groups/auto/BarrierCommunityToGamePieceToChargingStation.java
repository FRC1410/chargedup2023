package org.frc1410.chargedup2023.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BARRIER_COMMUNITY_START;

public class BarrierCommunityToGamePieceToChargingStation extends SequentialCommandGroup {
	public BarrierCommunityToGamePieceToChargingStation(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_START);

		addCommands(
			Trajectories.BarrierCommunityToGamePiece(drivetrain),
			new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
			Trajectories.BarrierGamePieceToChargingStation(drivetrain),
			new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
			new RunCommand(() -> {})
        );
	}
}