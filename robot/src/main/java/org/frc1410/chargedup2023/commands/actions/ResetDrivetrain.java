package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.ExternalCamera;

import static org.frc1410.chargedup2023.auto.POIs.BLUE_BARRIER_WAYPOINT;
import static org.frc1410.chargedup2023.auto.POIs.RED_OUTSIDE_WAYPOINT;

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
						pose.getY(),
						useVisionAngle ? Rotation2d.fromDegrees(-pose.getRotation().getDegrees()) : new Rotation2d(drivetrain.getHeading())
				)
//				new Pose2d(Units.inchesToMeters(490.38), Units.inchesToMeters(36.19), new Rotation2d(Units.degreesToRadians(-1.12)))
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
