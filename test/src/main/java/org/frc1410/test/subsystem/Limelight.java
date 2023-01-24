package org.frc1410.test.subsystem;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.test.util.NetworkTables;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;

import java.io.IOException;

public class Limelight implements TickedSubsystem {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Vision Data");

    DoublePublisher x = NetworkTables.PublisherFactory(table, "X", 0);
    DoublePublisher y = NetworkTables.PublisherFactory(table, "Y", 0);
    DoublePublisher z = NetworkTables.PublisherFactory(table, "Z", 0);
    DoublePublisher x2 = NetworkTables.PublisherFactory(table, "X2", 0);
    DoublePublisher y2 = NetworkTables.PublisherFactory(table, "Y2", 0);
    DoublePublisher z2 = NetworkTables.PublisherFactory(table, "Z2", 0);
    DoublePublisher angle = NetworkTables.PublisherFactory(table, "Angle", 0);

    Pose3d robotPose = new Pose3d();

    private final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
//    private final AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(List.of(
//            new AprilTag(2, new Pose3d(0, 0, 0, new Rotation3d()))
//    ), 16.53, 8.01);

    private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");

    private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
            fieldLayout, PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY, camera, new Transform3d()
    );

    public Limelight() throws IOException {
    }

    @Override
    public void periodic() {
        if (fieldLayout.getTagPose(camera.getLatestResult().getBestTarget().getFiducialId()).isPresent()) {
            robotPose = PhotonUtils.estimateFieldToRobotAprilTag(camera.getLatestResult().getBestTarget().getBestCameraToTarget(), fieldLayout.getTagPose(camera.getLatestResult().getBestTarget().getFiducialId()).orElseThrow(), new Transform3d());
        }

        poseEstimator.update().ifPresent(pose -> {
            x.set(Units.metersToInches(pose.estimatedPose.getX()));
            y.set(Units.metersToInches(pose.estimatedPose.getY()));
            z.set(Units.metersToInches(pose.estimatedPose.getZ()));
            x2.set(Units.metersToInches(robotPose.getX()));
            y2.set(Units.metersToInches(robotPose.getY()));
            z2.set(Units.metersToInches(robotPose.getX()));
            angle.set(Units.radiansToDegrees(pose.estimatedPose.getRotation().getAngle()));
        });
    }

    private enum LimelightMode {
        APRIL_TAG(0, VisionLEDMode.kOff),
        REFLECTIVE_TAPE(1, VisionLEDMode.kOn);

        private final int id;

        private final VisionLEDMode ledMode;

        LimelightMode(int id, VisionLEDMode ledMode) {
            this.id = id;
            this.ledMode = ledMode;
        }

        public static LimelightMode ofId(int id) {
            if (id == APRIL_TAG.id) {
                return APRIL_TAG;
            } else {
                return REFLECTIVE_TAPE;
            }
        }
    }

    public void setPipelineIndex(LimelightMode mode) {
        camera.setPipelineIndex(mode.id);
        camera.setLED(mode.ledMode);
    }

    public LimelightMode getMode() {
        return LimelightMode.ofId(camera.getPipelineIndex());
    }

    public void toggleMode() {
        var target = getMode() == LimelightMode.APRIL_TAG ? LimelightMode.REFLECTIVE_TAPE : LimelightMode.APRIL_TAG;
        setPipelineIndex(target);
    }
}