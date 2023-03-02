package org.frc1410.chargedup2023.util;

public interface Tuning {
	// Ramsete
	double KB = 2.0;
	double KZ = 0.7;

	double KP_VEL = 0.1;
	double KP_VEL_SLOW = 0.25;

	double MAX_SPEED = 1;
	double MAX_ACCEL = 1.0;

	// Engage
	double ENGAGE_P = 1;
	double ENGAGE_I = 0;
	double ENGAGE_D = 0.5;

	double ENGAGE_POSITION_TOLERANCE = 1;
	double ENGAGE_VELOCITY_TOLERANCE = 0;
	double ENGAGE_MAX_POWER = 0.25;

	// AprilTags
	double ANGLE_THRESHOLD = 10;

	// Elevator
	double ELEVATOR_KP = 3;
	double ELEVATOR_KI = 0;
	double ELEVATOR_KD = 0;
}