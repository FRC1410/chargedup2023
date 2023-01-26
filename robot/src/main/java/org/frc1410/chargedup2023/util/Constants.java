package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public interface Constants {
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double METERS_PER_REVOLUTION = .478778;
    double TRACKWIDTH = 0.615; //Meters
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

    // LBork rollers
    double LBORK_CONE_INTAKE_SPEED = 0.6;
    double LBORK_CONE_OUTTAKE_SPEED = 0.3;

    double LBORK_CUBE_INTAKE_SPEED = 0.6;
    double LBORK_CUBE_OUTTAKE_SPEED = 0.3;
}
