package org.frc1410.test.util;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Constants {
	
	int DRIVER_CONTROLLER = 0;
	int OPERATOR_CONTROLLER = 1;

	// DRIVETRAIN
	double KS = 0.67; // SysID: 0.55
	double KV = 2.67; // SysID: 2.15
	double KA = 0.34; // SysID: 0.30

	double KS_SLOW = 0.55;
	double KV_SLOW = 2.15;
	double KA_SLOW = 0.30;

	double GEARING = (11.0 / 62) * (24.0 / 54);
	double METERS_PER_REVOLUTION = .478778;
	double ENCODER_CONSTANT = GEARING * (1. / 2048.) * METERS_PER_REVOLUTION * 1.053;
	double TRACKWIDTH = 0.615; //Meters
	DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

	// AprilTags
	List<Integer> RED_TAGS = new ArrayList<>(List.of(1, 2, 3, 4));
	List<Integer> BLUE_TAGS = new ArrayList<>(List.of(5, 6, 7, 8));
	double WAYPOINT_THRESHOLD = -40; //Meters
}
