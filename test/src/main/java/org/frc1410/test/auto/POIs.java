package org.frc1410.test.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
    Pose2d START = new Pose2d(1, 2, Rotation2d.fromDegrees(0));
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(72.25), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(72.25), inchesToMeters(36.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_COMMUNITY_START_BACKWARD = new Pose2d(inchesToMeters(72.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_COMMUNITY_START_BACKWARD = new Pose2d(inchesToMeters(72.25), inchesToMeters(36.19), Rotation2d.fromDegrees(180));
    Pose2d TEST_1_METER = new Pose2d(2, 2, Rotation2d.fromDegrees(0));
    Pose2d TEST_2_METER = new Pose2d(3, 2, Rotation2d.fromDegrees(0));
    Pose2d TEST_QUARTER_CIRCLE = new Pose2d(3, 4, Rotation2d.fromDegrees(90));

    Pose2d BARRIER_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(172.8), Rotation2d.fromDegrees(45));
    Pose2d OUTSIDE_CHARGING_STATION_CORNER = new Pose2d(inchesToMeters(98.94), inchesToMeters(43.39), Rotation2d.fromDegrees(315));

    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(36.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(259.25), inchesToMeters(36.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.95), inchesToMeters(139.8), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.95), inchesToMeters(76.39), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(139.8), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(76.39), Rotation2d.fromDegrees(0));
}