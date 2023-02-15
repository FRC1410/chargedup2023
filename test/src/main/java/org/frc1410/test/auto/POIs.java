package org.frc1410.test.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.*;

public interface POIs {
	// Auto
	Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(119.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(73.25), inchesToMeters(295.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.25), inchesToMeters(163.31), Rotation2d.fromDegrees(-6));
	Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(73.25), inchesToMeters(251.31), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(135.31), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(135.31), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(279.31), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(261.25), inchesToMeters(279.31), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96+5.5), inchesToMeters(180+3), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96+4), inchesToMeters(235), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(180), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(235), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Pose2d(inchesToMeters(115.55), inchesToMeters(147.80-4.00), Rotation2d.fromDegrees(-31.57));
	// The x on these will probably be ~100 for the actual robot with bumpers, but starting at 110 for testing
	Translation2d OUTSIDE_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(110), inchesToMeters(275));

	// AprilTag Alignment
	Pose2d RED_LEFT_CONE_NODE = new Pose2d(-inchesToMeters(40), -inchesToMeters(24), new Rotation2d());
	Pose2d RED_CUBE_NODE = new Pose2d(-inchesToMeters(40), 0, new Rotation2d());
	Pose2d RED_RIGHT_CONE_NODE = new Pose2d(-inchesToMeters(40), inchesToMeters(24), new Rotation2d());
	Translation2d RED_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(0), inchesToMeters(0));
	Translation2d RED_OUTSIDE_WAYPOINT = new Translation2d(-inchesToMeters(70.5), -inchesToMeters(7));

	Pose2d BLUE_LEFT_CONE_NODE = new Pose2d(inchesToMeters(40), -inchesToMeters(24), Rotation2d.fromDegrees(180));
	Pose2d BLUE_CUBE_NODE = new Pose2d(inchesToMeters(40), 0, Rotation2d.fromDegrees(180));
	Pose2d BLUE_RIGHT_CONE_NODE = new Pose2d(inchesToMeters(40), inchesToMeters(24), Rotation2d.fromDegrees(180));
	Translation2d BLUE_BARRIER_WAYPOINT = new Translation2d(0, 0);
	Translation2d BLUE_OUTSIDE_WAYPOINT = new Translation2d(0, 0);
}