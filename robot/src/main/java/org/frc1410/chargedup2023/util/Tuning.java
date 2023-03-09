package org.frc1410.chargedup2023.util;

public interface Tuning {
	// Ramsete
	double KB = 2.0;
	double KZ = 0.7;

	double KP_VEL_AUTO = 3.5;
	double KP_VEL_TELEOP = 3;
	double KP_VEL_SLOW = 0.25;

	double MAX_SPEED_AUTO = 2.5;
	double MAX_ACCEL_AUTO = 2;

	double MAX_SPEED_TELEOP = 4;
	double MAX_ACCEL_TELEOP = 4; // 2.5 2

	// Engage
	double ENGAGE_P = 0.012;
	double ENGAGE_I = 0;
	double ENGAGE_D = 0.001;

	double ENGAGE_POSITION_TOLERANCE = 6;
	double ENGAGE_VELOCITY_TOLERANCE = 2;
	double ENGAGE_MAX_POWER = 0.25;

	// AprilTags
	double ANGLE_THRESHOLD = 4;

	// Elevator
	double ELEVATOR_KP = 3;
	double ELEVATOR_KI = 0;
	double ELEVATOR_KD = 0;
	double ELEVATOR_HOLD_KP = 1;
	double ELEVATOR_HOLD_KI = 0;
	double ELEVATOR_HOLD_KD = 0;

}