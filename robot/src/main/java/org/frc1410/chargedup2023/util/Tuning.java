package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N7;

public interface Tuning {
    // Ramsete
    double KB = 2.0; //3.0
    double KZ = 0.7; //2.0

    double KP_VEL = 0.25;
	double KP_VEL_SLOW = 0.25;
    double MAX_SPEED = 3.56; // 3.84 Theoretical Free Speed
    double MAX_ACCEL = 4.2; // Higher farther from top speed

//    Matrix<N7, N1> NOISE = VecBuilder.fill(0.005, 0.005, 0.005, 0.5, 0.5, 0.025, 0.025); // Too high for IRL?
//    Matrix<N7, N1> NOISE = VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005);
    Matrix<N7, N1> NOISE = VecBuilder.fill(0, 0, 0, 0, 0, 0, 0);

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
	double ELEVATOR_KP = 0;
	double ELEVATOR_KI = 0;
	double ELEVATOR_KD = 0;

	double ELEVATOR_MAX_OUTPUT = 0;
}