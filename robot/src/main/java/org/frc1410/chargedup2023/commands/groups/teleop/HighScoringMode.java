package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.HIGH_PAPA;
import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.targetPosition;
import static org.frc1410.chargedup2023.util.Constants.*;

public class HighScoringMode extends SequentialCommandGroup {

	public HighScoringMode(Drivetrain drivetrain, ExternalCamera camera, LBork lbork, Elevator elevator, Intake intake, TaskScheduler scheduler) {
		addCommands(
				new ParallelCommandGroup(
						targetPosition.equals(HIGH_PAPA)
							? new MoveElevator(lbork, elevator, intake, ELEVATOR_RAISED_POSITION, true)
								// THE ELEVATOR MIGHT NEED TO GO TO MID FOR HIGH CUBE
							: new MoveElevator(lbork, elevator, intake, ELEVATOR_RAISED_POSITION, true)
//						new GoToAprilTag(
//								drivetrain,
//								camera,
//								switch (targetPosition) {
//									case HIGH_LEFT_YANKEE -> GoToAprilTag.Node.LEFT_YANKEE_NODE;
//									case HIGH_PAPA -> GoToAprilTag.Node.PAPA_NODE;
//									case HIGH_RIGHT_YANKEE -> GoToAprilTag.Node.RIGHT_YANKEE_NODE;
//									default -> null;
//								},
//								scheduler
//						)
				)
//				new ParallelRaceGroup(
//						targetPosition.equals(HIGH_PAPA)
//								? new RunLBorkPapa(lbork, true)
//								: new RunLBorkYankee(lbork, true),
//						new WaitCommand(RUN_LBORK_SCORING_TIME)
//				)
		);
	}
}
