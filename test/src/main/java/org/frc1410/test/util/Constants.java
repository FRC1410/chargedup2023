package org.frc1410.test.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public interface Constants {
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double KS = 0.676;
    double KV = 2.67;
    double KA = 0.34;
    double GEARING = (11.0 / 62) * (24.0 / 54) * 1.057;
    double METERS_PER_REVOLUTION = .478778;
    double ENCODER_CONSTANT = GEARING * (1. / 2048.) * METERS_PER_REVOLUTION;
    double TRACKWIDTH = 0.615; //Meters
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);
}
