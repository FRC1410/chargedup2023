package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCone;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCube;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.*;

public class HybridScoringMode extends SequentialCommandGroup {
	public HybridScoringMode(Drivetrain drivetrain, ExternalCamera camera, LBork lbork, Elevator elevator, Intake intake, TaskScheduler scheduler) {
		addCommands(
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, Elevator.State.MID, false),
						new GoToAprilTag(
								drivetrain,
								camera,
								switch (targetPosition) {
									case HYBRID_LEFT -> GoToAprilTag.Node.LEFT_CONE_NODE;
									case HYBRID_MIDDLE -> GoToAprilTag.Node.CUBE_NODE;
									case HYBRID_RIGHT -> GoToAprilTag.Node.RIGHT_CONE_NODE;
									default -> null;
								},
								scheduler
						)
				),
				targetPosition.equals(HYBRID_MIDDLE)
					? new RunLBorkCube(lbork, true)
					: new RunLBorkCone(lbork, true)
		);
	}
}
