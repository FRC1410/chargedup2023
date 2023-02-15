package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;

import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;
import static org.frc1410.chargedup2023.util.Tuning.ANGLE_THRESHOLD;

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
								new Pose2d(pose.getX(), FIELD_WIDTH - pose.getY(), pose.getRotation()),
								time
						);
					}
				})
		);
	}
}