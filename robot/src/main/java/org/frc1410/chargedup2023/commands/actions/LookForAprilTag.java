package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.commands.actions.drivetrain.GoToAprilTag;
import org.frc1410.chargedup2023.commands.groups.teleop.SubstationScoringMode;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.framework.control.Button;
import org.frc1410.framework.control.observer.WhileHeldObserver;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.impl.CommandTask;
import org.frc1410.framework.scheduler.task.lock.LockPriority;

import static org.frc1410.chargedup2023.util.Constants.*;


public class LookForAprilTag extends CommandBase {

	private final Button button;
	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final LBork lbork;
	private final TaskScheduler scheduler;
	private final boolean rightBumper;

	public LookForAprilTag(Button button, Drivetrain drivetrain, ExternalCamera camera, LBork lbork, TaskScheduler scheduler, boolean rightBumper) {
		this.button = button;
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.lbork = lbork;
		this.scheduler = scheduler;
		this.rightBumper = rightBumper;
	}

	@Override
	public void initialize() {
		camera.getTargetLocation().ifPresent(pose -> {
			var observer = new WhileHeldObserver(button);

			// TODO: Replace GoToAprilTag command with the setup command
			if (camera.getTarget().getFiducialId() == 4 || camera.getTarget().getFiducialId() == 5) {
				scheduler.schedule(
					new CommandTask(new SubstationScoringMode(
						drivetrain,
						camera,
						lbork,
						scheduler,
						rightBumper
					)),
					TaskPersistence.EPHEMERAL,
					observer,
					LockPriority.HIGH
				);
			} else {
				scheduler.schedule(
					new CommandTask(new GoToAprilTag(
						drivetrain,
						camera,
						switch (ScoringPosition.targetPosition) {
							case HIGH_LEFT_CONE, MIDDLE_LEFT_CONE, HYBRID_LEFT -> GoToAprilTag.Node.LEFT_CONE_NODE;
							case HIGH_CUBE, MIDDLE_CUBE, HYBRID_MIDDLE -> GoToAprilTag.Node.CUBE_NODE;
							case HIGH_RIGHT_CONE, MIDDLE_RIGHT_CONE, HYBRID_RIGHT -> GoToAprilTag.Node.RIGHT_CONE_NODE;
						},
						scheduler
					)),
					TaskPersistence.EPHEMERAL,
					observer,
					LockPriority.HIGH
				);
			}
		});
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}