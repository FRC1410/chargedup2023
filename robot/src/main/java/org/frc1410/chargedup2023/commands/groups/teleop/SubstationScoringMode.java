package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.SUBSTATION_INTAKE_TIME;

public class SubstationScoringMode extends SequentialCommandGroup {
	public SubstationScoringMode(Drivetrain drivetrain, ExternalCamera camera, LBork lbork, TaskScheduler scheduler, boolean rightBumper) {
		addCommands(
				new GoToAprilTag(
					drivetrain,
					camera,
					rightBumper
						? GoToAprilTag.Node.RIGHT_SUBSTATION
						: GoToAprilTag.Node.LEFT_SUBSTATION,
					scheduler
				),
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, false),
						new WaitCommand(SUBSTATION_INTAKE_TIME)
				)
		);
	}
}
