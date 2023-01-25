package org.frc1410.test.subsystems;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
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
import org.photonvision.targeting.PhotonPipelineResult;

import java.util.List;
import java.util.Optional;

public class ExternalCamera implements TickedSubsystem {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Vision Data");

    private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");

    DoublePublisher x = NetworkTables.PublisherFactory(table, "X", 0);
    DoublePublisher y = NetworkTables.PublisherFactory(table, "Y", 0);
    DoublePublisher z = NetworkTables.PublisherFactory(table, "Z", 0);
    DoublePublisher angle = NetworkTables.PublisherFactory(table, "Angle", 0);
    //    private final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
    private final AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(List.of(
            new AprilTag(1, new Pose3d(0, 0, 25.5, new Rotation3d()))
    ), 16.53, 8.01);

    private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
            fieldLayout, PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE, camera,
            new Transform3d(new Translation3d(Units.inchesToMeters(16.5), 0, Units.inchesToMeters(25.5)), new Rotation3d())
    );

    @Override
    public void periodic() {
        poseEstimator.update().ifPresent(pose -> {
            x.set(Units.metersToInches(pose.estimatedPose.getX()));
            y.set(Units.metersToInches(pose.estimatedPose.getY()));
            z.set(Units.metersToInches(pose.estimatedPose.getZ()));
            angle.set(Units.radiansToDegrees(pose.estimatedPose.getRotation().getAngle()));
        });
        instance.flush();
    }

    public Optional<EstimatedRobotPose> getEstimatorPose(Pose2d pose) {
        poseEstimator.setReferencePose(pose);
        return poseEstimator.update();
    }

    public boolean hasTargets() {
        return camera.getLatestResult().hasTargets();
    }

    public PhotonPipelineResult getResult() {
        return camera.getLatestResult();
    }

    public double getTimestamp() {
        return camera.getLatestResult().getTimestampSeconds();
    }
}
