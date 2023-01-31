package org.frc1410.test.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.test.util.NetworkTables;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.io.IOException;
import java.util.Optional;

//import static org.frc1410.test.util.Constants.fieldLayout;

public class ExternalCamera implements TickedSubsystem {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Vision Data");

    private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
    // private final PhotonCamera camera = new PhotonCamera("OV9281");

	private static final AprilTagFieldLayout fieldLayout;

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

//    private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
//            fieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camera,
//            new Transform3d(new Translation3d(Units.inchesToMeters(16.5), 0, Units.inchesToMeters(25.5)), new Rotation3d())
//    );

    @Override
    public void periodic() {
		if (hasTargets()) {
			pose = (new Pose3d())
					.transformBy(camera.getLatestResult().getBestTarget().getBestCameraToTarget().inverse())
					.transformBy(new Transform3d(new Translation3d(Units.inchesToMeters(-16.5), 0, Units.inchesToMeters(25.5)), new Rotation3d()))
					.toPose2d();
		}

//        poseEstimator.update().ifPresent(pose -> {
//            x.set(Units.metersToInches(pose.estimatedPose.getX()));
//            y.set(Units.metersToInches(pose.estimatedPose.getY()));
//            angle.set(pose.estimatedPose.toPose2d().getRotation().getDegrees());
//        });
		x.set(Units.metersToInches(-pose.getX()));
		y.set(Units.metersToInches(-pose.getY()));
		angle.set(Units.radiansToDegrees(pose.getRotation().getRadians() > 0 ?
				Math.PI - pose.getRotation().getRadians() :
				-Math.PI - pose.getRotation().getRadians()));
        instance.flush();
    }

//    public Optional<EstimatedRobotPose> getEstimatorPose(Pose2d pose) {
//        poseEstimator.setReferencePose(new Pose2d(pose.getX(), 8.01 - pose.getY(), pose.getRotation()));
//        return poseEstimator.update();
//    }

	public Optional<Pose2d> getEstimatorPose() {
		return Optional.of(
				new Pose2d(
					-pose.getX(),
					pose.getY(),
					new Rotation2d(pose.getRotation().getRadians() > 0 ?
						Math.PI - pose.getRotation().getRadians() :
						-Math.PI - pose.getRotation().getRadians()))
		);
	}

    public boolean hasTargets() {
        return camera.getLatestResult().hasTargets();
    }

    public PhotonTrackedTarget getTarget() {
        return camera.getLatestResult().getBestTarget();
    }

    public Optional<Pose3d> getTargetLocation() {
        if (getTarget() != null) {
            return fieldLayout.getTagPose(getTarget().getFiducialId());
        }
        return Optional.empty();
    }

    public double getTimestamp() {
        return camera.getLatestResult().getTimestampSeconds();
    }
}
