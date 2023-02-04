package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N7;

public interface Tuning {
    // Ramsete
    double KB = 2.0; //3.0
    double KZ = 0.7; //2.0

    double KP_VEL = 0.5;
	double KP_VEL_SLOW = 0.25;
    double MAX_SPEED = 3.5; // 4.0 for FAST
    double MAX_ACCEL = 3.8; // 4.0 for FAST

//    Matrix<N7, N1> NOISE = VecBuilder.fill(0.005, 0.005, 0.005, 0.5, 0.5, 0.025, 0.025); // Too high for IRL?
    Matrix<N7, N1> NOISE = VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005);
//    Matrix<N7, N1> NOISE = VecBuilder.fill(0, 0, 0, 0, 0, 0, 0);
}