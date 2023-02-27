package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.commands.groups.teleop.HighScoringMode;
import org.frc1410.chargedup2023.commands.groups.teleop.HybridScoringMode;
import org.frc1410.chargedup2023.commands.groups.teleop.MidScoringMode;
import org.frc1410.chargedup2023.commands.groups.teleop.SubstationScoringMode;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.control.Button;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.control.observer.WhileHeldObserver;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.impl.CommandTask;
import org.frc1410.framework.scheduler.task.lock.LockPriority;

import static org.frc1410.chargedup2023.util.Constants.SCORING_TAGS;
import static org.frc1410.chargedup2023.util.Constants.ScoringPosition.targetPosition;
import static org.frc1410.chargedup2023.util.Constants.SUBSTATION_TAGS;


public class LookForAprilTag extends CommandBase {
	private final Controller controller;
	private final Button button;
	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final LBork lbork;
	private final Elevator elevator;
	private final Intake intake;
	private final TaskScheduler scheduler;
	private final boolean rightBumper;

	public LookForAprilTag(Controller controller, Button button, Drivetrain drivetrain, ExternalCamera camera, LBork lbork, Elevator elevator, Intake intake, TaskScheduler scheduler, boolean rightBumper) {
		this.controller = controller;
		this.button = button;
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.lbork = lbork;
		this.elevator = elevator;
		this.intake = intake;
		this.scheduler = scheduler;
		this.rightBumper = rightBumper;
	}

	@Override
	public void initialize() {
		camera.getTargetLocation().ifPresent(targetPose -> {
			var observer = new WhileHeldObserver(button);
			controller.rumble(1);

			if (!drivetrain.hasBeenReset()) {
				camera.getEstimatorPose().ifPresent(pose -> drivetrain.resetPoseEstimation(
						new Pose2d(
								pose.getX(),
								pose.getY(),
								drivetrain.getPoseEstimation().getRotation()
						)
				));
			}

			if (SUBSTATION_TAGS.contains(camera.getTarget().getFiducialId())) {
				scheduler.schedule(
					new CommandTask(
							new SubstationScoringMode(
								drivetrain,
								camera,
								lbork,
								elevator,
								intake,
								scheduler,
								rightBumper
							)
					),
					TaskPersistence.EPHEMERAL,
					observer,
					LockPriority.HIGH
				);
			} else if (SCORING_TAGS.contains(camera.getTarget().getFiducialId())) {
				scheduler.schedule(
					new CommandTask(
							switch (targetPosition) {
								case HIGH_LEFT_YANKEE, HIGH_PAPA, HIGH_RIGHT_YANKEE -> new HighScoringMode(
										drivetrain,
										camera,
										lbork,
										elevator,
										intake,
										scheduler
								);
								case MIDDLE_LEFT_YANKEE, MIDDLE_PAPA, MIDDLE_RIGHT_YANKEE -> new MidScoringMode(
										drivetrain,
										camera,
										lbork,
										elevator,
										intake,
										scheduler
								);
								case HYBRID_LEFT, HYBRID_MIDDLE, HYBRID_RIGHT -> new HybridScoringMode(
										drivetrain,
										camera,
										lbork,
										elevator,
										intake,
										scheduler
								);
							}
					),
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

	@Override
	public void end(boolean interrupted) {
		drivetrain.setReset(false);
	}
}