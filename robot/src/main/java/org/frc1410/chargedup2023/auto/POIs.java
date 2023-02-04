package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.25), inchesToMeters(152.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.25), inchesToMeters(251.31), Rotation2d.fromDegrees(0));

//    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(135.31), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(262.25), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(135.31), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(264.25), inchesToMeters(279.31), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(264.25), inchesToMeters(279.31), Rotation2d.fromDegrees(180));

//    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(180), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(146.6-11), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(235), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(180), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(235), Rotation2d.fromDegrees(0));

//    Pose2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Pose2d(inchesToMeters(115.55), inchesToMeters(147.80-4.00), Rotation2d.fromDegrees(-31.57));
    Translation2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(171.7+3)/*, Rotation2d.fromDegrees(31.57)*/);
}