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
	Pose2d BLUE_BARRIER_GRID = new Pose2d(inchesToMeters(74.25), inchesToMeters(196.19), Rotation2d.fromDegrees(180));
	Pose2d BLUE_OKLAHOMA = new Pose2d(inchesToMeters(293), inchesToMeters(235.5), Rotation2d.fromDegrees(-115));
	Translation2d BLUE_OKLAHOMA_MIDPOINT = new Translation2d(inchesToMeters(148.5), inchesToMeters(196.19));
	Translation2d BLUE_OKLAHOMA_MIDPOINT2 = new Translation2d(inchesToMeters(239.4), inchesToMeters(202));
	Translation2d BLUE_OKLAHOMA_G302 = new Translation2d(inchesToMeters(279.5), inchesToMeters(185));
	Translation2d BLUE_OKLAHOMA_BACK = new Translation2d(inchesToMeters(170), inchesToMeters(190));
	Translation2d BLUE_OKLAHOMA_GAMEPIECE = new Translation2d(inchesToMeters(278.25), inchesToMeters(180.2));
	Pose2d BLUE_BARRIER_SCORE_PAPA = new Pose2d(inchesToMeters(81), inchesToMeters(176.1), Rotation2d.fromDegrees(180));
	Pose2d BLUE_EXTERNAL_ENGAGE = new Pose2d(inchesToMeters(217), inchesToMeters(133.5), Rotation2d.fromDegrees(180));

	// Red
	Pose2d RED_BARRIER_GRID = new Pose2d(inchesToMeters(576.97), inchesToMeters(196.19), Rotation2d.fromDegrees(0));
	Pose2d RED_OKLAHOMA = new Pose2d(inchesToMeters(358.22), inchesToMeters(235.5), Rotation2d.fromDegrees(-65));
	Translation2d RED_OKLAHOMA_MIDPOINT = new Translation2d(inchesToMeters(502.72), inchesToMeters(196.19));
	Translation2d RED_OKLAHOMA_MIDPOINT2 = new Translation2d(inchesToMeters(411.82), inchesToMeters(202));
	Translation2d RED_OKLAHOMA_G302 = new Translation2d(inchesToMeters(371.72), inchesToMeters(185));
	Translation2d RED_OKLAHOMA_BACK = new Translation2d(inchesToMeters(481.22), inchesToMeters(190));
	Translation2d RED_OKLAHOMA_GAMEPIECE = new Translation2d(inchesToMeters(372.97), inchesToMeters(180.2));
	Pose2d RED_BARRIER_SCORE_PAPA = new Pose2d(inchesToMeters(570.22), inchesToMeters(176.1), Rotation2d.fromDegrees(0));
	Pose2d RED_EXTERNAL_ENGAGE = new Pose2d(inchesToMeters(434.22), inchesToMeters(133.5), Rotation2d.fromDegrees(0));

}