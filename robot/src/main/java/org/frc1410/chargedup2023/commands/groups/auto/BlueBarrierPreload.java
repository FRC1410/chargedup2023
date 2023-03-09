package org.frc1410.chargedup2023.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BLUE_BARRIER_GRID;
import static org.frc1410.chargedup2023.auto.POIs.RED_BARRIER_GRID;


public class BlueBarrierPreload extends SequentialCommandGroup {
	public BlueBarrierPreload(Drivetrain drivetrain) {
		drivetrain.resetPoseEstimation(RED_BARRIER_GRID);

		addCommands(
				Trajectories.RedBarrierGridToOklahoma(drivetrain),
				Trajectories.RedOklahomaToEngage(drivetrain)
		);
	}
}