package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.LinearSystemId;

public interface Constants {

    boolean LEFT_ENCODER_REVERSED = false;
    boolean RIGHT_ENCODER_REVERSED = false;
    int DRIVER_CONTROLLER = 0;
    int OPERATOR_CONTROLLER = 1;

    // DRIVETRAIN
    double KS = 0.676;
    double KV = 2.67;
    double KA = 0.34;
    double KV_ANGULAR = 2.84; //Simulation Only
    double KA_ANGULAR = 0.216; //Simulation Only

    double KS_SLOW = 0.55;
    double KV_SLOW = 2.15;
    double KA_SLOW = 0.30;

    double WHEEL_DIAMETER = 0.1524; //Meters //Simulation Only
    double ENCODER_CPR = 2048; //Simulation Only
    double ENCODER_EPR = 2048; //Simulation Only
    double GEARING = (11.0 / 62) * (24.0 / 54);
    double METERS_PER_REVOLUTION = .478778;
    double ENCODER_CONSTANT = GEARING * (1. / 2048.) * METERS_PER_REVOLUTION * 1.053;
    double ENCODER_DISTANCE_PER_PULSE = (WHEEL_DIAMETER * Math.PI) / ENCODER_CPR; //Simulation Only
//    double TRACKWIDTH = 0.615; //Meters
    double TRACKWIDTH = 0.615;
    DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

    LinearSystem<N2, N2, N2> DRIVETRAIN_PLANT = LinearSystemId.identifyDrivetrainSystem(
            KV, KA, KV_ANGULAR, KA_ANGULAR); //Simulation Only
}
