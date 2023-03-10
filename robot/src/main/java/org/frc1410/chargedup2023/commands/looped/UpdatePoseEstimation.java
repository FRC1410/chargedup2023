package org.frc1410.chargedup2023.commands.looped;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;
import static org.frc1410.chargedup2023.util.Tuning.ANGLE_THRESHOLD;

public class UpdatePoseEstimation extends CommandBase {

	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final LightBar lightBar;

	public UpdatePoseEstimation(Drivetrain drivetrain, ExternalCamera camera, LightBar lightBar) {
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.lightBar = lightBar;
	}

	@Override
	public void execute() {
		camera.getEstimatorPose().ifPresentOrElse(pose -> {
				if (camera.getEstimatorPose().isPresent()) {
					if (
							!(lightBar.get() == LightBar.Profile.SCORING.id) &&
							!(lightBar.get() == LightBar.Profile.SUBSTATION_NO_PIECE.id)
					) lightBar.set(LightBar.Profile.APRIL_TAG);
				} else if (
							!(lightBar.get() == LightBar.Profile.SCORING.id) &&
							!(lightBar.get() == LightBar.Profile.SUBSTATION_NO_PIECE.id)
				) lightBar.set(LightBar.Profile.IDLE_NO_PIECE);

				if ((Math.abs(Math.abs(drivetrain.getPoseEstimation().getRotation().getDegrees()) - Math.abs(-pose.getRotation().getDegrees())) <= ANGLE_THRESHOLD) && camera.hasTargets()) {
					drivetrain.addVisionPose(
							new Pose2d(pose.getX(), pose.getY(), drivetrain.getPoseEstimation().getRotation()),
							camera.getTimestamp()
					);
				}
			}, () -> System.out.println("Tried to update thing but no thing :(")
		);
	}
}