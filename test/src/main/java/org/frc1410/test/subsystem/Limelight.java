package org.frc1410.test.subsystem;

import edu.wpi.first.apriltag.AprilTag;
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
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Limelight implements TickedSubsystem {
    private final PhotonCamera camera = new PhotonCamera("photonvision");

    public Limelight() throws IOException {
    }

    @Override
    public void periodic() {
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