package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;

public interface POIs {
	Pose2d START = new Pose2d(.0, .0, Rotation2d.fromDegrees(0));

	// 2023 Robot
	Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(73.75), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(73.75), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_EXIT = new Pose2d(inchesToMeters(160), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_EXIT = new Pose2d(inchesToMeters(220), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(212), inchesToMeters(183), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(212), inchesToMeters(235), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(135.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(279.31), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(135.31), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(279.31), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.75), inchesToMeters(163.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.75), inchesToMeters(251.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.44), inchesToMeters(183), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(95.44), inchesToMeters(235), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(183.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(258.75), inchesToMeters(231.31), Rotation2d.fromDegrees(0));

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