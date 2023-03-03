package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;
import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;

public interface POIs {
	Pose2d START = new Pose2d(.0, .0, Rotation2d.fromDegrees(0));

	// 2023 Robot
	Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(163.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(251.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE_PAPA = new Pose2d(inchesToMeters(74.25), inchesToMeters(141.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE_PAPA = new Pose2d(inchesToMeters(74.25), inchesToMeters(273.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(135.31), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(135.31), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(279.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(279.31), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(135.31), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(135.31), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(279.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(279.31), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(180), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(235), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(180), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(235), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_SCORE_YANKEE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(143.859), Rotation2d.fromDegrees(17.91));
	Pose2d OUTSIDE_SCORE_YANKEE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(270.761), Rotation2d.fromDegrees(-17.91)); //D*NE

	Pose2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(150.12), Rotation2d.fromDegrees(-48));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(265.38), Rotation2d.fromDegrees(48));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_BACKWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(150.12), Rotation2d.fromDegrees(-48+180));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_BACKWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(265.38), Rotation2d.fromDegrees(48-180));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_INTAKE_ANGLED = new Pose2d(inchesToMeters(260.67), inchesToMeters(164.98), Rotation2d.fromDegrees(-48+180));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_INTAKE_ANGLED = new Pose2d(inchesToMeters(260.67), inchesToMeters(250.52), Rotation2d.fromDegrees(48-180));

	Translation2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT = new Translation2d(inchesToMeters(167.48), inchesToMeters(128.33465));
	Translation2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT = new Translation2d(inchesToMeters(167.48), inchesToMeters(28.33465));

	Translation2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(135.5));
	Translation2d OUTSIDE_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(280.5));

	Translation2d BARRIER_PAPA_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(138.31));
	Translation2d OUTSIDE_PAPA_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(277.19));

	// AprilTag Alignment
	Pose2d RED_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(40), -inchesToMeters(22), new Rotation2d());
	Pose2d RED_PAPA_NODE = new Pose2d(inchesToMeters(40), 0, new Rotation2d());
	Pose2d RED_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(40), inchesToMeters(22), new Rotation2d());
	Translation2d RED_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(523), FIELD_WIDTH - inchesToMeters(132));
	Translation2d RED_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(523), inchesToMeters(41));
	Pose2d RED_LEFT_SUBSTATION = new Pose2d(inchesToMeters(50.529), inchesToMeters(23.808), Rotation2d.fromDegrees(180));
	Pose2d RED_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(50.529), -inchesToMeters(24.46), Rotation2d.fromDegrees(180));

	Pose2d BLUE_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(40), -inchesToMeters(22), Rotation2d.fromDegrees(180));
	Pose2d BLUE_PAPA_NODE = new Pose2d(inchesToMeters(40), 0, Rotation2d.fromDegrees(180));
	Pose2d BLUE_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(40), inchesToMeters(22), Rotation2d.fromDegrees(180));
	Translation2d BLUE_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(132));
	Translation2d BLUE_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(284.5));
	Pose2d BLUE_LEFT_SUBSTATION = new Pose2d(inchesToMeters(50.529), inchesToMeters(24.46), new Rotation2d());
	Pose2d BLUE_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(50.529), -inchesToMeters(23.808), new Rotation2d());
}