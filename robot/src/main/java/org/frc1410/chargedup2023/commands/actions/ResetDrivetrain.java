package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;

public class ResetDrivetrain extends CommandBase {
	private final Drivetrain drivetrain;
	private final ExternalCamera camera;

	public ResetDrivetrain(Drivetrain drivetrain, ExternalCamera camera) {
		this.drivetrain = drivetrain;
		this.camera = camera;
	}

	@Override
	public void initialize() {
		camera.getEstimatorPose().ifPresent(pose -> drivetrain.resetPoseEstimation(
				new Pose2d(
						pose.getX(),
						pose.getY(),
						drivetrain.getPoseEstimation().getRotation()
				)
		));
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean interrupted) {
		drivetrain.setReset(true);
	}
}
