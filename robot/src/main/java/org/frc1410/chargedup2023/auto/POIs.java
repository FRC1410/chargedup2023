package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;

public interface POIs {
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(20.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(20.31), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(152.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(64.19), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(174.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(42.19), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(26.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(26.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(26.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(26.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(146.6-11), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(235), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(180), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(235), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.25), inchesToMeters(132.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.25), inchesToMeters(84.19), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_MIDDLE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(258.25), inchesToMeters(132.19), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(258.25), inchesToMeters(84.19), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_MIDDLE_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(258.25-20), inchesToMeters(132.19), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_MIDDLE_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(258.25-20), inchesToMeters(132.19), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_MIDDLE_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(258.25-20), inchesToMeters(84.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_MIDDLE_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(258.25-20), inchesToMeters(84.19), Rotation2d.fromDegrees(180));

	Pose2d NUCLEAR_OPTION_POSE = new Pose2d(inchesToMeters(74.685), inchesToMeters(171.641), Rotation2d.fromDegrees(17.91));

	Pose2d NUCLEAR_WASTE_PICKUP = new Pose2d(inchesToMeters(247.29), inchesToMeters(165.38), Rotation2d.fromDegrees(-48));
	Pose2d NUCLEAR_WASTE_PICKUP_BACKWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(165.38), Rotation2d.fromDegrees(-48+180));
	Pose2d NUCLEAR_WASTE_INTAKE = new Pose2d(inchesToMeters(260.67), inchesToMeters(150.52), Rotation2d.fromDegrees(-48+180));
	Translation2d NUCLEAR_MIDPOINT = new Translation2d(4.254, 4.754);


    Translation2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(180));
	Translation2d OUTSIDE_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(35));

	Translation2d BARRIER_CUBE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(177.19));
}