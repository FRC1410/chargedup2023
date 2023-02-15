package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.test.commands.groups.OTFToPoint;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Constants.*;
import static org.frc1410.test.auto.POIs.*;

public class GoToAprilTag extends CommandBase {
	public enum Node {
		LEFT_YANKEE_NODE,
		PAPA_NODE,
		RIGHT_YANKEE_NODE
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
					case LEFT_YANKEE_NODE -> {
						if (camera.getTarget().getFiducialId() == 1 && waypointFlag)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, RED_OUTSIDE_WAYPOINT, RED_LEFT_YANKEE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, RED_LEFT_YANKEE_NODE));
					}
					case PAPA_NODE ->
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, RED_PAPA_NODE));
					case RIGHT_YANKEE_NODE -> {
						if (camera.getTarget().getFiducialId() == 3 && waypointFlag)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, RED_BARRIER_WAYPOINT, RED_RIGHT_YANKEE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, RED_RIGHT_YANKEE_NODE));
					}
				}
			} else {
				switch (targetNode) {
					case LEFT_YANKEE_NODE -> {
						if (camera.getTarget().getFiducialId() == 6)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, BLUE_BARRIER_WAYPOINT, BLUE_LEFT_YANKEE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, BLUE_LEFT_YANKEE_NODE));
					}
					case PAPA_NODE ->
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, BLUE_PAPA_NODE));
					case RIGHT_YANKEE_NODE -> {
						if (camera.getTarget().getFiducialId() == 8)
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, BLUE_OUTSIDE_WAYPOINT, BLUE_RIGHT_YANKEE_NODE));
						else
							scheduler.scheduleAutoCommand(new OTFToPoint(drivetrain, BLUE_RIGHT_YANKEE_NODE));
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
