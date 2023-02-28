package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;

import static org.frc1410.chargedup2023.auto.POIs.BLUE_BARRIER_WAYPOINT;
import static org.frc1410.chargedup2023.auto.POIs.RED_OUTSIDE_WAYPOINT;
import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;

public class ResetDrivetrain extends CommandBase {
	private final Drivetrain drivetrain;
	private final ExternalCamera camera;
	private final boolean useVisionAngle;

	public ResetDrivetrain(Drivetrain drivetrain, ExternalCamera camera, boolean useVisionAngle) {
		this.drivetrain = drivetrain;
		this.camera = camera;
		this.useVisionAngle = useVisionAngle;
	}

	@Override
	public void initialize() {
		camera.getEstimatorPose().ifPresent(pose -> drivetrain.resetPoseEstimation(
				new Pose2d(
						pose.getX(),
						FIELD_WIDTH - pose.getY(),
						useVisionAngle ? pose.getRotation() : drivetrain.getPoseEstimation().getRotation()
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
