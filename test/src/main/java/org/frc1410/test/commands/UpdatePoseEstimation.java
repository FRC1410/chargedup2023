package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Tuning.ANGLE_THRESHOLD;

public class UpdatePoseEstimation extends CommandBase {

	private final Drivetrain drivetrain;
	private final ExternalCamera camera;

	public UpdatePoseEstimation(Drivetrain drivetrain, ExternalCamera camera) {
		this.drivetrain = drivetrain;
		this.camera = camera;
	}

	@Override
	public void execute() {
		camera.getEstimatorPose().ifPresent(pose -> camera.getTimestamp().ifPresent(time -> {
				if (Math.abs(drivetrain.getHeading() - pose.getRotation().getDegrees()) <= ANGLE_THRESHOLD && camera.hasTargets()) {
					drivetrain.addVisionPose(
							new Pose2d(pose.getX(), Units.inchesToMeters(315.5) - pose.getY(), pose.getRotation()),
							time
					);
				}
			})
		);
	}
}