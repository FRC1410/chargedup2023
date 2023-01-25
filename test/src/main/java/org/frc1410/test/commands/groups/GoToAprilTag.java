package org.frc1410.test.commands.groups;

import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.commands.OTFToPoint;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.subsystems.ExternalCamera;

import static org.frc1410.test.util.Constants.*;

public class GoToAprilTag extends SequentialCommandGroup {
    public GoToAprilTag(Drivetrain drivetrain, ExternalCamera camera) {
        camera.getTargetLocation().ifPresent(pose -> {
            if (RED_TAGS.contains(camera.getTarget().getFiducialId())) {
                addCommands(
                        new OTFToPoint(drivetrain, pose.toPose2d().plus(new Transform2d(new Translation2d(Units.inchesToMeters(-24), 0), new Rotation2d()))),
                        new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
                );
            } else {
                addCommands(
                        new OTFToPoint(drivetrain, pose.toPose2d().plus(new Transform2d(new Translation2d(Units.inchesToMeters(24), 0), new Rotation2d()))),
                        new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
                );
            }
        });
    }
}
