package org.frc1410.test.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(20.19), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(212.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(212.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(264.25), inchesToMeters(4.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(264.25), inchesToMeters(4.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(256.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(-40.19), Rotation2d.fromDegrees(0)); // THESE COORDINATES ASSUME FLIPPED Y

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(139.8), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(76.39), Rotation2d.fromDegrees(0));
}