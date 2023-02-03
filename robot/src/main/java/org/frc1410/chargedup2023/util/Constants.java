package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

import java.util.ArrayList;
import java.util.List;

public interface Constants {
	
	int DRIVER_CONTROLLER = 0;
	int OPERATOR_CONTROLLER = 1;

	// DRIVETRAIN
	double KS = 0;
	double KV = 0;
	double KA = 0;

	double KS_SLOW = 0;
	double KV_SLOW = 0;
	double KA_SLOW = 0;

	double METERS_PER_REVOLUTION = .478778;
	double TRACKWIDTH = 0.615; //Meters
	DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

	// LBork rollers
	double LBORK_CONE_INTAKE_SPEED = 0.6;
	double LBORK_CONE_OUTTAKE_SPEED = 0.3;

	double LBORK_CUBE_INTAKE_SPEED = 0.6;
	double LBORK_CUBE_OUTTAKE_SPEED = 0.3;

	// AprilTags
	List<Integer> RED_TAGS = new ArrayList<>(List.of(1, 2, 3, 4));
	List<Integer> BLUE_TAGS = new ArrayList<>(List.of(5, 6, 7, 8));

	// Elevator
	double ELEVATOR_SPEED = 0.5;
}
