package org.frc1410.test.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public interface Constants {
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double KS = 0.55;
    double KV = 2.15; // 2.67
    double KA = 0.30; // 0.34 (0.3 seems decent)
    double GEARING = (11.0 / 62) * (24.0 / 54);
    double METERS_PER_REVOLUTION = .478778;
    double ENCODER_CONSTANT = GEARING * (1. / 2048.) * METERS_PER_REVOLUTION * 1.053;
    double TRACKWIDTH = 0.615; //Meters
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);
}
