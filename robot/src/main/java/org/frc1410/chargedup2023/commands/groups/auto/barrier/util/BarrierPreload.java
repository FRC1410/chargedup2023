package org.frc1410.chargedup2023.commands.groups.auto.barrier.util;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCone;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.OUTSIDE_COMMUNITY_START;
import static org.frc1410.chargedup2023.util.Constants.OUTTAKE_TIME;

public class BarrierPreload extends SequentialCommandGroup {
	public BarrierPreload(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		drivetrain.resetPoseEstimation(OUTSIDE_COMMUNITY_START);

		addCommands(
				new MoveElevator(lbork, elevator, intake, Elevator.State.RAISED, true),
				Trajectories.BarrierCommunityToGrid(drivetrain),
				new ParallelRaceGroup(
						new RunLBorkCone(lbork, true),
						new WaitCommand(OUTTAKE_TIME)
				),
				new RetractLBork(lbork)
		);
	}
}