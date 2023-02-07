package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Constants;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.impl.CommandTask;

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
				)
		);
	}
}
