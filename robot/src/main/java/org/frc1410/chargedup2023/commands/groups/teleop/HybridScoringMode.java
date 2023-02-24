package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.*;
import static org.frc1410.chargedup2023.util.Constants.*;

public class HybridScoringMode extends SequentialCommandGroup {
	public HybridScoringMode(Drivetrain drivetrain, ExternalCamera camera, LBork lbork, Elevator elevator, Intake intake, TaskScheduler scheduler) {
		addCommands(
				new ExtendIntake(intake),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, ELEVATOR_MID_POSITION, false)
//						new GoToAprilTag(
//								drivetrain,
//								camera,
//								switch (targetPosition) {
//									case HYBRID_LEFT -> GoToAprilTag.Node.LEFT_YANKEE_NODE;
//									case HYBRID_MIDDLE -> GoToAprilTag.Node.PAPA_NODE;
//									case HYBRID_RIGHT -> GoToAprilTag.Node.RIGHT_YANKEE_NODE;
//									default -> null;
//								},
//								scheduler
//						)
				),
				new ParallelRaceGroup(
						targetPosition.equals(HYBRID_MIDDLE)
								? new RunLBorkPapa(lbork, true)
								: new RunLBorkYankee(lbork, true),
						new WaitCommand(RUN_LBORK_SCORING_TIME)
				)
		);
	}
}
