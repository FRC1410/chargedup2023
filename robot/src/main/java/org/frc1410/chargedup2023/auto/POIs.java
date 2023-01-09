package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public interface POIs {
    Pose2d START = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_1_METER = new Pose2d(1, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_90_DEGREES = new Pose2d(0, 0, Rotation2d.fromDegrees(90));
}