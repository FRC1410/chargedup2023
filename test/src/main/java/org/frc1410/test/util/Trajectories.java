package org.frc1410.test.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.frc1410.test.subsystem.Drivetrain;

import java.util.List;

import static org.frc1410.test.util.Constants.*;
import static org.frc1410.test.util.Tuning.*;
import static org.frc1410.test.auto.POIs.*;

public interface Trajectories {

    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Drivetrain");
    DoublePublisher leftMeasurementPub = Networktables.PublisherFactory(table, "Left Measurement", 0);
    DoublePublisher leftReferencePub = Networktables.PublisherFactory(table, "Left Desired", 0);
    DoublePublisher rightMeasurementPub = Networktables.PublisherFactory(table, "Right Measurement", 0);
    DoublePublisher rightReferencePub = Networktables.PublisherFactory(table, "Right Desired", 0);

    CentripetalAccelerationConstraint centripetalAccelConstraint = new CentripetalAccelerationConstraint(0.75);
    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .addConstraint(centripetalAccelConstraint)
        .setReversed(false);

    TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .setReversed(true);


    RamseteController disabledRamsete = new RamseteController(KB, KZ);
    PIDController leftController = new PIDController(KP_VEL, 0, 0);
    PIDController rightController = new PIDController(KP_VEL, 0, 0);
    static RamseteCommand baseRamsete(Trajectory trajectory, Drivetrain drivetrain) {

        disabledRamsete.setEnabled(true);
        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                disabledRamsete,
                new SimpleMotorFeedforward(KS, KV, KA),
                KINEMATICS,
                drivetrain::getWheelSpeeds,
                leftController,
                rightController,
//                drivetrain::tankDriveVolts,
                (leftVolts, rightVolts) -> {
                    drivetrain.tankDriveVolts(leftVolts, rightVolts);

                    leftMeasurementPub.set(drivetrain.getWheelSpeeds().leftMetersPerSecond);
                    leftReferencePub.set(leftController.getSetpoint());

                    rightMeasurementPub.set(drivetrain.getWheelSpeeds().rightMetersPerSecond);
                    rightReferencePub.set(rightController.getSetpoint());
                    instance.flush();
                }
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
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_QUARTER_CIRCLE), config), drivetrain);
    }

    static RamseteCommand testSCurve(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE), config), drivetrain);
    }

    static RamseteCommand testSCurveLarge(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_LARGE), reverseConfig), drivetrain);
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