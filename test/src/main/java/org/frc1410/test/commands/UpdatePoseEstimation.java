package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

public class UpdatePoseEstimation extends CommandBase {

	private final Drivetrain drivetrain;
	private final ExternalCamera camera;

	public UpdatePoseEstimation(Drivetrain drivetrain, ExternalCamera camera) {
		this.drivetrain = drivetrain;
		this.camera = camera;
	}

	@Override
	public void execute() {
		camera.getEstimatorPose(drivetrain.getPoseEstimation())
				.ifPresent(pose -> drivetrain.addVisionPose(
						new Pose2d(
								pose.estimatedPose.toPose2d().getX(),
								8.01 - pose.estimatedPose.toPose2d().getY(),
								drivetrain.getPoseEstimation().getRotation()),
						camera.getTimestamp()));
	}
}
