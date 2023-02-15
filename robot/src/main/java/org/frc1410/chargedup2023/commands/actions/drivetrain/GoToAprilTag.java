package org.frc1410.chargedup2023.commands.actions.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.chargedup2023.commands.groups.teleop.OTFToPoint;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.auto.POIs.*;

public class GoToAprilTag extends CommandBase {
	public enum Node {
		LEFT_CONE_NODE,
		CUBE_NODE,
		RIGHT_CONE_NODE,
		LEFT_SUBSTATION,
		RIGHT_SUBSTATION
	}

	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final Node targetNode;
	private final TaskScheduler scheduler;
	private boolean waypointFlag = false;

	public GoToAprilTag(Drivetrain drivetrain, ExternalCamera camera, Node targetNode, TaskScheduler scheduler) {
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.targetNode = targetNode;
		this.scheduler = scheduler;
	}

	@Override
	public void initialize() {
		camera.getTargetLocation().ifPresent(pose -> {
			waypointFlag = drivetrain.getPoseEstimation().getX() < WAYPOINT_THRESHOLD;

			if (RED_TAGS.contains(camera.getTarget().getFiducialId())) {
				switch (targetNode) {
					case LEFT_CONE_NODE -> {
						if (camera.getTarget().getFiducialId() == 1 && waypointFlag)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_OUTSIDE_WAYPOINT, RED_LEFT_CONE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_LEFT_CONE_NODE));
					}
					case CUBE_NODE ->
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_CUBE_NODE));
					case RIGHT_CONE_NODE -> {
						if (camera.getTarget().getFiducialId() == 3 && waypointFlag)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_INSIDE_WAYPOINT, RED_RIGHT_CONE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_RIGHT_CONE_NODE));
					}
					case LEFT_SUBSTATION -> {
						if (camera.getTarget().getFiducialId() == 5)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_LEFT_SUBSTATION));
					}
					case RIGHT_SUBSTATION -> {
						if (camera.getTarget().getFiducialId() == 5)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), RED_RIGHT_SUBSTATION));
					}
				}
			} else {
				switch (targetNode) {
					case LEFT_CONE_NODE -> {
						if (camera.getTarget().getFiducialId() == 6)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_INSIDE_WAYPOINT, BLUE_LEFT_CONE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_LEFT_CONE_NODE));
					}
					case CUBE_NODE ->
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_CUBE_NODE));
					case RIGHT_CONE_NODE -> {
						if (camera.getTarget().getFiducialId() == 8)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_OUTSIDE_WAYPOINT, BLUE_RIGHT_CONE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_RIGHT_CONE_NODE));
					}
					case LEFT_SUBSTATION -> {
						if (camera.getTarget().getFiducialId() == 4)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_LEFT_SUBSTATION));
					}
					case RIGHT_SUBSTATION -> {
						if (camera.getTarget().getFiducialId() == 4)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, pose.toPose2d(), BLUE_RIGHT_SUBSTATION));
					}
				}
			}
		});
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.tankDriveVolts(0, 0);
	}
}
