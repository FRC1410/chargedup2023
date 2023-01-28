package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Constants.FIELD_WIDTH;

public class UpdatePoseEstimation extends CommandBase {

	private final Drivetrain drivetrain;
	private final ExternalCamera camera;

	public UpdatePoseEstimation(Drivetrain drivetrain, ExternalCamera camera) {
		this.drivetrain = drivetrain;
		this.camera = camera;
	}

	@Override
	public void execute() {
//		camera.getEstimatorPose(drivetrain.getPoseEstimation())
//				.ifPresent(pose -> drivetrain.addVisionPose(
//						new Pose2d(
//								pose.estimatedPose.toPose2d().getX(),
//								FIELD_WIDTH - pose.estimatedPose.toPose2d().getY(),
//								pose.estimatedPose.toPose2d().getRotation()),
////								drivetrain.getPoseEstimation().getRotation()),
//						camera.getTimestamp()));
		camera.getEstimatorPose().ifPresent(pose -> drivetrain.addVisionPose(
				new Pose2d(
						pose.getX(),
						FIELD_WIDTH - pose.getY(),
						pose.getRotation()),
//								drivetrain.getPoseEstimation().getRotation()),
				camera.getTimestamp()));
	}
}
