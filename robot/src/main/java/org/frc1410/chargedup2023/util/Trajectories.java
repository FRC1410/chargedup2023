package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.frc1410.chargedup2023.subsystem.Drivetrain;

import java.util.List;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;
import static org.frc1410.chargedup2023.auto.POIs.*;

public interface Trajectories {
    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .setReversed(false);

    TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .setReversed(true);

    static RamseteCommand baseRamsete(Trajectory trajectory, Drivetrain drivetrain) {
        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                new RamseteController(KB, KZ),
                new SimpleMotorFeedforward(KS, KV, KA),
                KINEMATICS,
                drivetrain::getWheelSpeeds,
                new PIDController(KP_VEL, 0, 0, 10.0 / 1000),
                new PIDController(KP_VEL, 0, 0, 10.0 / 1000),
                drivetrain::tankDriveVolts
        );
    }

    static RamseteCommand test1Meter(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_1_METER), config), drivetrain);
    }

    static RamseteCommand test2Meter(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_2_METER), config), drivetrain);
    }

//    static RamseteCommand test90Degrees(Drivetrain drivetrain) {
//        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_90_DEGREES), config), drivetrain);
//    }

    static RamseteCommand mobility(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_1_METER), config), drivetrain);
    }
}