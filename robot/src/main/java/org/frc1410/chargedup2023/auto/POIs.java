package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;

public interface POIs {
	Pose2d START = new Pose2d(.0, .0, Rotation2d.fromDegrees(0));

	// 2023 Robot
	Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(163.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(251.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(141.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(273.31), Rotation2d.fromDegrees(0));

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

	Pose2d BARRIER_SCORE_CONE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(143.859), Rotation2d.fromDegrees(17.91));
	Pose2d OUTSIDE_SCORE_CONE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(270.761), Rotation2d.fromDegrees(-17.91)); //D*NE

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

	Translation2d BARRIER_CUBE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(138.31));
	Translation2d OUTSIDE_CUBE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(277.19));

	// AprilTag Alignment
	Pose2d RED_LEFT_CONE_NODE = new Pose2d(-inchesToMeters(40), -inchesToMeters(24), new Rotation2d());
	Pose2d RED_CUBE_NODE = new Pose2d(-inchesToMeters(40), 0, new Rotation2d());
	Pose2d RED_RIGHT_CONE_NODE = new Pose2d(-inchesToMeters(40), inchesToMeters(24), new Rotation2d());
	Translation2d RED_INSIDE_WAYPOINT = new Translation2d(inchesToMeters(0), inchesToMeters(0));
	Translation2d RED_OUTSIDE_WAYPOINT = new Translation2d(-inchesToMeters(70.5), -inchesToMeters(7));
	Pose2d RED_LEFT_SUBSTATION = new Pose2d();
	Pose2d RED_RIGHT_SUBSTATION = new Pose2d();

	Pose2d BLUE_LEFT_CONE_NODE = new Pose2d(inchesToMeters(40), -inchesToMeters(24), Rotation2d.fromDegrees(180));
	Pose2d BLUE_CUBE_NODE = new Pose2d(inchesToMeters(40), 0, Rotation2d.fromDegrees(180));
	Pose2d BLUE_RIGHT_CONE_NODE = new Pose2d(inchesToMeters(40), inchesToMeters(24), Rotation2d.fromDegrees(180));
	Translation2d BLUE_INSIDE_WAYPOINT = new Translation2d(0, 0);
	Translation2d BLUE_OUTSIDE_WAYPOINT = new Translation2d(0, 0);
	Pose2d BLUE_LEFT_SUBSTATION = new Pose2d();
	Pose2d BLUE_RIGHT_SUBSTATION = new Pose2d();
}