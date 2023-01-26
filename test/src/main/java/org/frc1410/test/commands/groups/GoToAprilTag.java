package org.frc1410.test.commands.groups;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Constants.*;

public class GoToAprilTag extends SequentialCommandGroup {
    public GoToAprilTag(Drivetrain drivetrain, ExternalCamera camera) {
        camera.getTargetLocation().ifPresent(pose -> {
            if (RED_TAGS.contains(camera.getTarget().getFiducialId())) {
                addCommands(
                        new OTFToPoint(drivetrain, new Pose2d(pose.getX() + Units.inchesToMeters(-36), pose.getY(), pose.getRotation().toRotation2d().rotateBy(Rotation2d.fromDegrees(180))))
                );
            } else {
                addCommands(
                        new OTFToPoint(drivetrain, new Pose2d(pose.getX() + Units.inchesToMeters(36), pose.getY(), pose.getRotation().toRotation2d()))
                );
            }
        });
    }
}
