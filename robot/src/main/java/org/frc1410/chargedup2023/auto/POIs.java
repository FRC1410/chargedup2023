package org.frc1410.chargedup2023.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import static edu.wpi.first.math.util.Units.inchesToMeters;

public interface POIs {
    Pose2d BARRIER_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_START = new Pose2d(inchesToMeters(74.25+7), inchesToMeters(20.19), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(20.19), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(152.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_COMMUNITY_SCORE = new Pose2d(inchesToMeters(74.25), inchesToMeters(64.19), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(174.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_COMMUNITY_SCORE_CUBE = new Pose2d(inchesToMeters(74.25), inchesToMeters(42.19), Rotation2d.fromDegrees(0));

	Pose2d BARRIER_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
	Pose2d BARRIER_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
	Pose2d OUTSIDE_GAME_PIECE_FORWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(36.19), Rotation2d.fromDegrees(0));
	Pose2d OUTSIDE_GAME_PIECE_BACKWARD_MIDPOINT = new Pose2d(inchesToMeters(254.25-20), inchesToMeters(36.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(180.19), Rotation2d.fromDegrees(0));
    Pose2d BARRIER_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(180.19), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_GAME_PIECE_FORWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(36.19), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_GAME_PIECE_BACKWARD = new Pose2d(inchesToMeters(254.25), inchesToMeters(36.19), Rotation2d.fromDegrees(180));

    Pose2d BARRIER_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(135.5), Rotation2d.fromDegrees(0));
    Pose2d OUTSIDE_CHARGING_STATION_COMMUNITY = new Pose2d(inchesToMeters(96), inchesToMeters(80.5), Rotation2d.fromDegrees(0));

    Pose2d BARRIER_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(135.5), Rotation2d.fromDegrees(180));
    Pose2d OUTSIDE_CHARGING_STATION_FAR = new Pose2d(inchesToMeters(210.12), inchesToMeters(80.5), Rotation2d.fromDegrees(180));

	Pose2d BARRIER_SCORE_CONE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(171.641), Rotation2d.fromDegrees(17.91));
	Pose2d OUTSIDE_SCORE_CONE_ANGLED = new Pose2d(inchesToMeters(74.685), inchesToMeters(44.739), Rotation2d.fromDegrees(-17.91)); //D*NE

	Pose2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(165.38), Rotation2d.fromDegrees(-48));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(50.12), Rotation2d.fromDegrees(48));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_BACKWARD = new Pose2d(inchesToMeters(247.29), inchesToMeters(165.38), Rotation2d.fromDegrees(-48+180));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_BACKWARD = new Pose2d(inchesToMeters(247.29+5), inchesToMeters(50.12+5), Rotation2d.fromDegrees(48-180));

	Pose2d BARRIER_MIDDLE_GAME_PIECE_INTAKE_ANGLED = new Pose2d(inchesToMeters(260.67), inchesToMeters(150.52), Rotation2d.fromDegrees(-48+180));
	Pose2d OUTSIDE_MIDDLE_GAME_PIECE_INTAKE_ANGLED = new Pose2d(inchesToMeters(260.67+5), inchesToMeters(64.98+5), Rotation2d.fromDegrees(48-180));

	Translation2d BARRIER_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT = new Translation2d(4.254, 4.754);
	Translation2d OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT = new Translation2d(4.254, 0.71970011);

    Translation2d BARRIER_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(180));
	Translation2d OUTSIDE_GAME_PIECE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(115.55), inchesToMeters(35));

	Translation2d BARRIER_CUBE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(177.19));
	Translation2d OUTSIDE_CUBE_SCORE_MIDPOINT = new Translation2d(inchesToMeters(215), inchesToMeters(38.31));

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
}