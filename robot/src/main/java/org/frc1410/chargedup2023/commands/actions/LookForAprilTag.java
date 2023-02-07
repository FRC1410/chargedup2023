package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.framework.control.Button;
import org.frc1410.framework.scheduler.task.TaskScheduler;

import static org.frc1410.chargedup2023.util.Constants.*;


public class LookForAprilTag extends CommandBase {
	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final TaskScheduler scheduler;
	private final boolean rightBumper;

	public LookForAprilTag(Drivetrain drivetrain, ExternalCamera camera, TaskScheduler scheduler, boolean rightBumper) {
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.scheduler = scheduler;
		this.rightBumper = rightBumper;
	}

	@Override
	public void initialize() {
		camera.getTargetLocation().ifPresent(pose -> {
			// TODO: Replace GoToAprilTag command with the setup command
			if (camera.getTarget().getFiducialId() == 4 || camera.getTarget().getFiducialId() == 5) {
				if (!rightBumper) {
					scheduler.scheduleAutoCommand(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.LEFT_SUBSTATION, scheduler));
				} else {
					scheduler.scheduleAutoCommand(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.RIGHT_SUBSTATION, scheduler));
				}
			} else {
				switch (ScoringPosition.targetPosition) {
					case HIGH_LEFT_CONE, MIDDLE_LEFT_CONE, HYBRID_LEFT ->
						scheduler.scheduleAutoCommand(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.LEFT_CONE_NODE, scheduler));
					case HIGH_CUBE, MIDDLE_CUBE, HYBRID_MIDDLE ->
						scheduler.scheduleAutoCommand(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.CUBE_NODE, scheduler));
					case HIGH_RIGHT_CONE, MIDDLE_RIGHT_CONE, HYBRID_RIGHT ->
						scheduler.scheduleAutoCommand(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.RIGHT_CONE_NODE, scheduler));
				}
			}
		});
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
