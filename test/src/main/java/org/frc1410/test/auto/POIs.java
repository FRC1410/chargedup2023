package org.frc1410.test.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
    Pose2d START = new Pose2d(0, 0, Rotation2d.fromDegrees(0));

    Pose2d TEST_1_METER = new Pose2d(1, 0, Rotation2d.fromDegrees(0));
    Pose2d TEST_2_METER = new Pose2d(2, 0, Rotation2d.fromDegrees(0));

    Pose2d TEST_S_CURVE_1x1_SHORT = new Pose2d(1,1, Rotation2d.fromDegrees(0));
    Pose2d TEST_S_CURVE_1x1_LONG = new Pose2d(2,2, Rotation2d.fromDegrees(0));
    Pose2d TEST_S_CURVE_1x2_SHORT = new Pose2d(1, 0.5, Rotation2d.fromDegrees(0));
    Pose2d TEST_S_CURVE_1x2_LONG = new Pose2d(2, 1, Rotation2d.fromDegrees(0));
    Pose2d TEST_S_CURVE_Nx0_SHORT = new Pose2d(0, 1, Rotation2d.fromDegrees(12));
    Pose2d TEST_S_CURVE_Nx0_LONG = new Pose2d(0, 2, Rotation2d.fromDegrees(0));

    Pose2d TEST_ARC_60_SHORT = new Pose2d(0.433012701892, 0.25, Rotation2d.fromDegrees(60));
    Pose2d TEST_ARC_60_LONG = new Pose2d(1.299, 0.75, Rotation2d.fromDegrees(60));
    Pose2d TEST_ARC_180_SHORT = new Pose2d(0, -1, Rotation2d.fromDegrees(180));
    Pose2d TEST_ARC_180_LONG = new Pose2d(0, -3, Rotation2d.fromDegrees(180));

    // REAL TRAJECTORIES
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(75.25), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(72.25), inchesToMeters(20.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_COMMUNITY_START_BACKWARD = new Pose2d(inchesToMeters(72.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_COMMUNITY_START_BACKWARD = new Pose2d(inchesToMeters(72.25), inchesToMeters(36.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(172.8), Rotation2d.fromDegrees(45));
    Pose2d OUTSIDE_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(43.39), Rotation2d.fromDegrees(315));
    // THESE COORDINATES ASSUME FLIPPED Y
    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(212.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    // THESE COORDINATES ASSUME FLIPPED Y
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(4.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(36.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.95), inchesToMeters(139.8), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.95), inchesToMeters(76.39), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(139.8), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(76.39), Rotation2d.fromDegrees(0));


}