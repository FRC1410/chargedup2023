package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.test.commands.groups.OTFToPoint;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Constants.*;

public class GoToAprilTag extends CommandBase {
	
	Drivetrain drivetrain;
	ExternalCamera camera;
	TaskScheduler scheduler;
	public GoToAprilTag(Drivetrain drivetrain, ExternalCamera camera, TaskScheduler scheduler) {
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.scheduler = scheduler;
	}

	@Override
	public void initialize() {
		camera.getTargetLocation().ifPresent(pose -> {
			if (RED_TAGS.contains(camera.getTarget().getFiducialId())) {
				scheduler.scheduleAutoCommand(new OTFToPoint(
						drivetrain,
						new Pose2d(pose.getX() + Units.inchesToMeters(-40), pose.getY(),
								pose.getRotation().toRotation2d().rotateBy(Rotation2d.fromDegrees(180)))));
			} else {
				scheduler.scheduleAutoCommand(new OTFToPoint(
						drivetrain,
						new Pose2d(pose.getX() + Units.inchesToMeters(40), pose.getY(),
								pose.getRotation().toRotation2d())));
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
