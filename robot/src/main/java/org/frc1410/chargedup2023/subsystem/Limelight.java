package org.frc1410.chargedup2023.subsystem;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import static org.frc1410.chargedup2023.util.Constants.*;

public class Limelight implements TickedSubsystem, Subsystem {
    private PhotonCamera camera = new PhotonCamera("Photonvision");
    private PhotonPipelineResult result;
    private PhotonTrackedTarget target;

    public Limelight() {

    }

    @Override
    public void periodic() {
        result = camera.getLatestResult();
        target = result.getBestTarget();
    }

    public double getYaw() {
        return target.getYaw();
    }

    public double getPitch() {
        return target.getPitch();
    }

    public double getDistance() {
        return PhotonUtils.calculateDistanceToTargetMeters(
                CAMERA_HEIGHT,
                TARGET_HEIGHT,
                CAMERA_PITCH,
                Units.degreesToRadians(getPitch()));
    }

    public int getTargetID() {
        return target.getFiducialId();
    }

    public void setLEDs(boolean enabled) {
        if (enabled) camera.setLED(VisionLEDMode.kOn);
        else camera.setLED(VisionLEDMode.kOff);
    }
}
