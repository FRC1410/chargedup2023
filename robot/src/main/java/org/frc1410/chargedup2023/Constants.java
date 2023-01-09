package org.frc1410.chargedup2023;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public interface Constants {
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double GEARING = (11.0 / 62) * (24.0 / 54) * 1.057;
    double METERS_PER_REVOLUTION = .478778;
    double ENCODER_CONSTANT = (1 / GEARING) * (1. / 2048.) * METERS_PER_REVOLUTION;
    double TRACKWIDTH = 0.6907; //Meters
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);
}
