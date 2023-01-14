package org.frc1410.test.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;

public class LimeLight implements Subsystem {

    private final PhotonCamera camera = new PhotonCamera("Photonvision");

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