package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
		camera.getEstimatorPose().ifPresentOrElse(pose -> {
//			System.out.println("Cam has targets? " + camera.hasTargets());
					if (Math.abs(Math.abs(drivetrain.getPoseEstimation().getRotation().getDegrees()) - Math.abs(pose.getRotation().getDegrees())) <= ANGLE_THRESHOLD && camera.hasTargets()) {
						drivetrain.addVisionPose(
								new Pose2d(pose.getX(), FIELD_WIDTH - pose.getY(), drivetrain.getPoseEstimation().getRotation()),
								camera.getTimestamp()
						);
//						System.out.println(drivetrain.getHeading() + "HEADING");
					}
				}, () -> System.out.println("Tried to update thinf but no thing :(")
		);
	}
}