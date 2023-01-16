package org.frc1410.test.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.frc1410.test.subsystem.Drivetrain;

import java.util.List;

import static org.frc1410.test.util.Constants.*;
import static org.frc1410.test.util.Tuning.*;
import static org.frc1410.test.auto.POIs.*;

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
                new PIDController(KP_VEL, 0, 0, 20.0 / 1000),
                new PIDController(KP_VEL, 0, 0, 20.0 / 1000),
                drivetrain::tankDriveVolts
        );
    }

    static RamseteCommand test1Meter(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_1_METER), config), drivetrain);
    }

    static RamseteCommand test2Meter(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_2_METER), config), drivetrain);
    }

    static RamseteCommand test2MeterBack(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(TEST_2_METER, START), reverseConfig), drivetrain);
    }

    static RamseteCommand testQuarterCircle(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(TEST_1_METER, TEST_QUARTER_CIRCLE), config), drivetrain);
    }

    static RamseteCommand testQuarterCircleBack(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(TEST_QUARTER_CIRCLE, START), reverseConfig), drivetrain);
    }

    static RamseteCommand mobility(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_1_METER), config), drivetrain);
    }

    static RamseteCommand BarrierCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_GAME_PIECE_FORWARD), config), drivetrain);
    }

    static RamseteCommand BarrierGamePieceToCommunity(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_COMMUNITY_START), reverseConfig), drivetrain);
    }

    static RamseteCommand OutsideCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_GAME_PIECE_FORWARD), config), drivetrain);
    }

    static RamseteCommand OutsideGamePieceToCommunity(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_FORWARD, OUTSIDE_COMMUNITY_START), reverseConfig), drivetrain);
    }

    static RamseteCommand OutsideGamePieceToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_FORWARD, OUTSIDE_CHARGING_STATION_FAR), reverseConfig), drivetrain);
    }

    static RamseteCommand BarrierGamePieceToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_CHARGING_STATION_FAR), reverseConfig), drivetrain);
    }
}