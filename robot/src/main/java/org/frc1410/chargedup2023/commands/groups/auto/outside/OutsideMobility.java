package org.frc1410.chargedup2023.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.OUTSIDE_COMMUNITY_START;

public class OutsideMobility extends SequentialCommandGroup {
	public OutsideMobility(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(OUTSIDE_COMMUNITY_START);

		addCommands(
				Trajectories.BarrierMobility(drivetrain),
				new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
		);
	}
}