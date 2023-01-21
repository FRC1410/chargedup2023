package org.frc1410.test.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.photonvision.PhotonCamera;

public class Camera implements TickedSubsystem {

    private final PhotonCamera camera = new PhotonCamera("Photonvision");

    public boolean hasTarget() {
        return camera.getLatestResult().hasTargets();
    }

    public void Camera() {
        SmartDashboard.putBoolean("Has Target", hasTarget());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Has Target", hasTarget());
    }
}