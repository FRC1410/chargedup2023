package org.frc1410.chargedup2023.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.io.IOException;
import java.util.Optional;

public class ExternalCamera implements TickedSubsystem {

	private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
	private final NetworkTable table = instance.getTable("Vision Data");
	private final DoublePublisher idPub = NetworkTables.PublisherFactory(table, "AprilTag ID", 0);

	private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");

	private static final AprilTagFieldLayout fieldLayout;

	//
	PhotonPoseEstimator photonPoseEstimator = new PhotonPoseEstimator(
			fieldLayout,
			PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY,
			camera,
			new Transform3d(
					new Translation3d(Units.inchesToMeters(12.5), Units.inchesToMeters(-9.25), Units.inchesToMeters(28)),
					new Rotation3d(0, Units.degreesToRadians(-6), 0))
	);

	private Pose2d pose = new Pose2d();

	static {
		try {
			fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	DoublePublisher x = NetworkTables.PublisherFactory(table, "X", 0);
	DoublePublisher y = NetworkTables.PublisherFactory(table, "Y", 0);
	DoublePublisher angle = NetworkTables.PublisherFactory(table, "Angle", 0);

	@Override
	public void periodic() {
		photonPoseEstimator.update().ifPresent(pose -> this.pose = pose.estimatedPose.toPose2d());

		x.set(Units.metersToInches(pose.getX()));
		y.set(Units.metersToInches(pose.getY()));
		angle.set(Units.radiansToDegrees(-pose.getRotation().getRadians()));
		instance.flush();
	}

	public Optional<Pose2d> getEstimatorPose() {
		return Optional.of(new Pose2d(
				pose.getX(),
				pose.getY(),
				new Rotation2d(-pose.getRotation().getRadians())));
	}

	public boolean hasTargets() {
		return camera.getLatestResult().hasTargets();
	}

	public PhotonTrackedTarget getTarget(Pose2d referencePose) {
		var referenceTranslation = referencePose.getTranslation();
		double minDistance = 10000000;
		PhotonTrackedTarget bestTarget = null;

		for (var target : camera.getLatestResult().getTargets()) {
			var fieldPose = fieldLayout.getTagPose(target.getFiducialId());
			if (fieldPose.isPresent()) {
				var distance = referenceTranslation.getDistance(fieldPose.get().toPose2d().getTranslation());
				if (distance < minDistance) {
					minDistance = distance;
					bestTarget = target;
				}
			}
		}
		if (bestTarget != null) idPub.set(bestTarget.getFiducialId());

		return bestTarget;
	}

	public Optional<Pose3d> getTargetLocation(Pose2d referencePose) {
		if (getTarget(referencePose) != null) {
			return fieldLayout.getTagPose(getTarget(referencePose).getFiducialId());
		}
		return Optional.empty();
	}

	public double getTimestamp() {
		return camera.getLatestResult().getTimestampSeconds();
	}
}
