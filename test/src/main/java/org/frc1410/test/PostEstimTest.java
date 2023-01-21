package org.frc1410.test;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.io.IOException;
import java.util.List;

public class PostEstimTest extends PhaseDrivenRobot {

    private final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2022RapidReact.m_resourceFile);
    private final AprilTagFieldLayout correctedLayout = new AprilTagFieldLayout(List.of(
            new AprilTag(1, new Pose3d(15.49, 1.07, 0, new Rotation3d()))
    ), 16.53, 8.01);
    private final PhotonCamera camera = new PhotonCamera("Microsoft_LifeCam_HD-3000");
    private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(
            fieldLayout, PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY, camera, new Transform3d()
    );

    {
        subsystems.track(new Reporter(poseEstimator));
    }

    public PostEstimTest() throws IOException {}

    @Override
    protected void testSequence() {

    }
}

record Reporter(PhotonPoseEstimator estimator) implements TickedSubsystem {

    @Override
    public void periodic() {
        estimator().update().ifPresent(pose -> {
            System.out.println("Got a pose read: " + pose.estimatedPose);
        });
    }
}