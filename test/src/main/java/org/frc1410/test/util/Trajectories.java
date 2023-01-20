package org.frc1410.test.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
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
    DoublePublisher leftMeasurementPub = NetworkTables.PublisherFactory(table, "Left Measurement", 0);
    DoublePublisher leftReferencePub = NetworkTables.PublisherFactory(table, "Left Desired", 0);
    DoublePublisher rightMeasurementPub = NetworkTables.PublisherFactory(table, "Right Measurement", 0);
    DoublePublisher rightReferencePub = NetworkTables.PublisherFactory(table, "Right Desired", 0);

//    CentripetalAccelerationConstraint centripetalAccelConstraint = new CentripetalAccelerationConstraint(0.75);
    DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 11);
    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .addConstraint(voltageConstraint)
//        .addConstraint(centripetalAccelConstraint)
        .setReversed(false);

    TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .addConstraint(voltageConstraint)
//        .addConstraint(centripetalAccelConstraint)
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

    static RamseteCommand testSCurveNx0Short(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_Nx0_SHORT), config), drivetrain);
    }

    static RamseteCommand testSCurveNx0Long(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_Nx0_LONG), config), drivetrain);
    }

    static RamseteCommand testSCurve1x1Short(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_1x1_SHORT), config), drivetrain);
    }

    static RamseteCommand testSCurve1x1Long(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_1x1_LONG), config), drivetrain);
    }

    static RamseteCommand testSCurve1x2Short(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_1x2_SHORT), config), drivetrain);
    }

    static RamseteCommand testSCurve1x2Long(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_S_CURVE_1x2_LONG), config), drivetrain);
    }

    static RamseteCommand testArc60Short(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_ARC_60_SHORT), config), drivetrain);
    }

    static RamseteCommand testArc60Long(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_ARC_60_LONG), config), drivetrain);
    }

    static RamseteCommand testArc180Short(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_ARC_180_SHORT), config), drivetrain);
    }

    static RamseteCommand testArc180Long(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_ARC_180_LONG), config), drivetrain);
    }


    // REAL TRAJECTORIES
    static RamseteCommand mobility(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(START, TEST_1_METER), config), drivetrain);
    }

    static RamseteCommand BarrierCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_GAME_PIECE_BACKWARD), config), drivetrain);
    }

    static RamseteCommand BarrierGamePieceToCommunity(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_COMMUNITY_START), reverseConfig), drivetrain);
    }

    static RamseteCommand OutsideCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_GAME_PIECE_BACKWARD), config), drivetrain);
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