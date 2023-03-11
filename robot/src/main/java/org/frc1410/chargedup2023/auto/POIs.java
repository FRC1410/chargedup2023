package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

import static edu.wpi.first.math.util.Units.inchesToMeters;
import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;

public interface POIs {
	// Auto
	// Blue
	Pose2d BLUE_BARRIER_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(192.19), Rotation2d.fromDegrees(180));
	Pose2d BLUE_OKLAHOMA = new Pose2d(inchesToMeters(293), inchesToMeters(235.5), Rotation2d.fromDegrees(-115));
	Translation2d BLUE_OKLAHOMA_MIDPOINT = new Translation2d(inchesToMeters(148.5), inchesToMeters(192.19));
	Translation2d BLUE_OKLAHOMA_MIDPOINT2 = new Translation2d(inchesToMeters(239.4), inchesToMeters(202));
	Translation2d BLUE_OKLAHOMA_G302 = new Translation2d(inchesToMeters(279.5), inchesToMeters(185));
	Translation2d BLUE_OKLAHOMA_BACK = new Translation2d(inchesToMeters(170), inchesToMeters(190));
	Translation2d BLUE_OKLAHOMA_GAMEPIECE = new Translation2d(inchesToMeters(278.25), inchesToMeters(180.2));
	Pose2d BLUE_BARRIER_SCORE_PAPA = new Pose2d(inchesToMeters(81), inchesToMeters(176.1), Rotation2d.fromDegrees(180));
	Pose2d BLUE_EXTERNAL_ENGAGE = new Pose2d(inchesToMeters(217), inchesToMeters(133.5), Rotation2d.fromDegrees(180));

	// Red
	Pose2d RED_BARRIER_GRID = new Pose2d(inchesToMeters(576.97), inchesToMeters(192.19), Rotation2d.fromDegrees(0));
	Pose2d RED_OKLAHOMA = new Pose2d(inchesToMeters(358.22), inchesToMeters(235.5), Rotation2d.fromDegrees(-65));
	Translation2d RED_OKLAHOMA_MIDPOINT = new Translation2d(inchesToMeters(502.72), inchesToMeters(192.19));
	Translation2d RED_OKLAHOMA_MIDPOINT2 = new Translation2d(inchesToMeters(411.82), inchesToMeters(202));
	Translation2d RED_OKLAHOMA_G302 = new Translation2d(inchesToMeters(371.72), inchesToMeters(185));
	Translation2d RED_OKLAHOMA_BACK = new Translation2d(inchesToMeters(481.22), inchesToMeters(190));
	Translation2d RED_OKLAHOMA_GAMEPIECE = new Translation2d(inchesToMeters(372.97), inchesToMeters(180.2));
	Pose2d RED_BARRIER_SCORE_PAPA = new Pose2d(inchesToMeters(570.22), inchesToMeters(176.1), Rotation2d.fromDegrees(0));
	Pose2d RED_EXTERNAL_ENGAGE = new Pose2d(inchesToMeters(434.22), inchesToMeters(133.5), Rotation2d.fromDegrees(0));

	// AprilTag Alignment
	Pose2d RED_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(40.5), -inchesToMeters(22), new Rotation2d());
	Pose2d RED_PAPA_NODE = new Pose2d(inchesToMeters(39.5), 0, new Rotation2d());
	Pose2d RED_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(40.5), inchesToMeters(22), new Rotation2d());
	Translation2d RED_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(525), FIELD_WIDTH - inchesToMeters(132)); //TODO
	Translation2d RED_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(525), inchesToMeters(34));
	Pose2d RED_LEFT_SUBSTATION = new Pose2d(inchesToMeters(52), -inchesToMeters(33), Rotation2d.fromDegrees(180));
	Pose2d RED_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(52), inchesToMeters(33), Rotation2d.fromDegrees(180));

	Pose2d BLUE_LEFT_YANKEE_NODE = new Pose2d(inchesToMeters(40.5), -inchesToMeters(22), Rotation2d.fromDegrees(180));
	Pose2d BLUE_PAPA_NODE = new Pose2d(inchesToMeters(39.5), 0, Rotation2d.fromDegrees(180));
	Pose2d BLUE_RIGHT_YANKEE_NODE = new Pose2d(inchesToMeters(40.5), inchesToMeters(22), Rotation2d.fromDegrees(180));
	Translation2d BLUE_BARRIER_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(132)); //TODO
	Translation2d BLUE_OUTSIDE_WAYPOINT = new Translation2d(inchesToMeters(115), FIELD_WIDTH - inchesToMeters(284.5)); //TODO
	Pose2d BLUE_LEFT_SUBSTATION = new Pose2d(inchesToMeters(52), -inchesToMeters(33), new Rotation2d());
	Pose2d BLUE_RIGHT_SUBSTATION = new Pose2d(inchesToMeters(52), inchesToMeters(33), new Rotation2d());
}