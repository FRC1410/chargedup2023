package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCone;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCube;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.*;
import static org.frc1410.chargedup2023.util.Tuning.RUN_LBORK_SCORING_TIME;

public class MidScoringMode extends SequentialCommandGroup {

	public MidScoringMode(Drivetrain drivetrain, ExternalCamera camera, LBork lbork, Elevator elevator, Intake intake, TaskScheduler scheduler) {
		addCommands(
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, Elevator.State.MID, false),
						new GoToAprilTag(
								drivetrain,
								camera,
								switch (targetPosition) {
									case MIDDLE_LEFT_CONE -> GoToAprilTag.Node.LEFT_CONE_NODE;
									case MIDDLE_CUBE -> GoToAprilTag.Node.CUBE_NODE;
									case MIDDLE_RIGHT_CONE -> GoToAprilTag.Node.RIGHT_CONE_NODE;
									default -> null;
								},
								scheduler
						)
				),
				new ParallelRaceGroup(
						targetPosition.equals(MIDDLE_CUBE)
								? new RunLBorkCube(lbork, true)
								: new RunLBorkCone(lbork, true),
						new WaitCommand(RUN_LBORK_SCORING_TIME)
				)
		);
	}
}
