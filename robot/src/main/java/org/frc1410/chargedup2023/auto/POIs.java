package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
    Pose2d START = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_1_METER = new Pose2d(1, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_2_METER = new Pose2d(2, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_QUARTER_CIRCLE = new Pose2d(1, 1, Rotation2d.fromDegrees(90));

    Pose2d BARRIER_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(172.8), Rotation2d.fromDegrees(45));
    Pose2d OUTSIDE_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(43.39), Rotation2d.fromDegrees(315));
}