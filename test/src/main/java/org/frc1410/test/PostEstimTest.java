package org.frc1410.test;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc1410.test.util.NetworkTables;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;

import java.io.IOException;
import java.util.List;

public class PostEstimTest extends PhaseDrivenRobot {

//    private final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
    private final AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(List.of(
            new AprilTag(2, new Pose3d(0, 0, 0, new Rotation3d()))
    ), 16.53, 8.01);
    private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
    private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
            fieldLayout, PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY, camera, new Transform3d()
    );

    {
        subsystems.track(new Reporter(poseEstimator, fieldLayout, camera));
    }

    public PostEstimTest() throws IOException {}

    @Override
    protected void testSequence() {

    }
}

record Reporter(PhotonPoseEstimator estimator, AprilTagFieldLayout fieldLayout, PhotonCamera camera) implements TickedSubsystem {
    static NetworkTableInstance instance = NetworkTableInstance.getDefault();
    static NetworkTable table = instance.getTable("Vision Data");

    static DoublePublisher x = NetworkTables.PublisherFactory(table, "X", 0);
    static DoublePublisher y = NetworkTables.PublisherFactory(table, "Y", 0);
    static DoublePublisher z = NetworkTables.PublisherFactory(table, "Z", 0);
    static DoublePublisher x2 = NetworkTables.PublisherFactory(table, "X2", 0);
    static DoublePublisher y2 = NetworkTables.PublisherFactory(table, "Y2", 0);
    static DoublePublisher z2 = NetworkTables.PublisherFactory(table, "Z2", 0);
    static DoublePublisher angle = NetworkTables.PublisherFactory(table, "Angle", 0);
    static Pose3d robotPose;


    @Override
    public void periodic() {
        robotPose = PhotonUtils.estimateFieldToRobotAprilTag(camera.getLatestResult().getBestTarget().getBestCameraToTarget(), , new Transform3d());

        fieldLayout.getTagPose(camera.getLatestResult().getBestTarget().getFiducialId()).orElseThrow();

        estimator().update().ifPresent(pose -> {
            x.set(Units.metersToInches(pose.estimatedPose.getX()));
            y.set(Units.metersToInches(pose.estimatedPose.getY()));
            z.set(Units.metersToInches(pose.estimatedPose.getZ()));
            x2.set(Units.metersToInches(robotPose.getX()));
            x2.set(Units.metersToInches(robotPose.getY()));
            x2.set(Units.metersToInches(robotPose.getX()));
            angle.set(Units.radiansToDegrees(pose.estimatedPose.getRotation().getAngle()));
        });
    }
}