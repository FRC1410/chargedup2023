package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N7;

public interface Tuning {
    // Ramsete
	double KB = 5.0; // 5.0
    double KZ = 2.0; // 2.0

    double KP_VEL = 2;
    // ALL FOLLOWING MAX(2,3), standard b, zeta
    // 1 = 15 deg error
    // 0.5 = 7.5deg error
    // 0.25 = 2deg error
    // 0.125 = 0.85deg error
    // 0.0 = 2.8deg error
    // This could mean that the feedback is poor, and the feedforward is good
    // This may be less realistic because the KS/KV/KA could be wrong IRL

    // If MAX isn't specified, MAX(1,1)
    // KP=2.0,2M | 8% error | 5% error MAX(2,3) | 6% error MAX(1,3) | 5% error MAX(3,3)
    // KP=0.5,2M | 11.5% error | 7.5% error MAX(2,3) | 9% error MAX(1,3) | 7.5% error MAX(3,3)
    // KP=2.0,1M | 16% error | 10% error MAX(2,3) | 11.1% error MAX(1,3) | 10% error MAX(3,3)
    // KP=0.5,1M | 23.8% error | 16% error MAX(2,3) | 16% error MAX(1,3) | 15% error MAX(3,3)

    double MAX_SPEED = 2.0;
    double MAX_ACCEL = 3.0; // Falcon drivetrain

//    Matrix<N7, N1> NOISE = VecBuilder.fill(0.005, 0.005, 0.005, 0.5, 0.5, 0.025, 0.025);
    Matrix<N7, N1> NOISE = VecBuilder.fill(0, 0, 0, 0, 0, 0, 0);
}