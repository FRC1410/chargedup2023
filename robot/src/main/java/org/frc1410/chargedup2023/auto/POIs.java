package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;
import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;

public interface POIs {
	// Auto
	Pose2d BARRIER_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(196.19), new Rotation2d(0));
	Pose2d OKLAHOMA = new Pose2d(inchesToMeters(293), inchesToMeters(235.5), Rotation2d.fromDegrees(65));
	Translation2d OKLAHOMA_MIDPOINT = new Translation2d(inchesToMeters(148.5), inchesToMeters(196.19));
	Translation2d OKLAHOMA_MIDPOINT2 = new Translation2d(inchesToMeters(239.4), inchesToMeters(202));
	Translation2d OKLAHOMA_G302 = new Translation2d(inchesToMeters(279.5), inchesToMeters(190.8));
	Pose2d BARRIER_SCORE_PAPA = new Pose2d(inchesToMeters(74.25), inchesToMeters(176.19), new Rotation2d(0));


	// AprilTag Alignment
	Pose2d RED_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(45), -inchesToMeters(22), new Rotation2d());
	Pose2d RED_PAPA_NODE = new Pose2d(inchesToMeters(45), 0, new Rotation2d());
	Pose2d RED_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(45), inchesToMeters(22), new Rotation2d());
	Translation2d RED_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(523), FIELD_WIDTH - inchesToMeters(132));
	Translation2d RED_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(523), inchesToMeters(41));
	Pose2d RED_LEFT_SUBSTATION = new Pose2d(inchesToMeters(50.529), inchesToMeters(23.808), Rotation2d.fromDegrees(180));
	Pose2d RED_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(50.529), -inchesToMeters(24.46), Rotation2d.fromDegrees(180));

	Pose2d BLUE_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(45), -inchesToMeters(22), Rotation2d.fromDegrees(180));
	Pose2d BLUE_PAPA_NODE = new Pose2d(inchesToMeters(45), 0, Rotation2d.fromDegrees(180));
	Pose2d BLUE_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(45), inchesToMeters(22), Rotation2d.fromDegrees(180));
	Translation2d BLUE_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(132));
	Translation2d BLUE_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(284.5));
	Pose2d BLUE_LEFT_SUBSTATION = new Pose2d(inchesToMeters(50.529), inchesToMeters(24.46), new Rotation2d());
	Pose2d BLUE_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(50.529), -inchesToMeters(23.808), new Rotation2d());
}