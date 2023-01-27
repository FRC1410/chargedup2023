package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.frc1410.chargedup2023.subsystem.Drivetrain;

import java.util.List;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;
import static org.frc1410.chargedup2023.auto.POIs.*;

public interface Trajectories {

    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Drivetrain");
    DoublePublisher leftMeasurementPub = NetworkTables.PublisherFactory(table, "Left Measurement", 0);
    DoublePublisher leftReferencePub = NetworkTables.PublisherFactory(table, "Left Desired", 0);
    DoublePublisher rightMeasurementPub = NetworkTables.PublisherFactory(table, "Right Measurement", 0);
    DoublePublisher rightReferencePub = NetworkTables.PublisherFactory(table, "Right Desired", 0);

    CentripetalAccelerationConstraint centripetalAccelConstraint = new CentripetalAccelerationConstraint(0.75);
    DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 11);

    DifferentialDriveVoltageConstraint slowVoltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 5);
    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
            .setKinematics(KINEMATICS)
            .addConstraint(voltageConstraint)
            .setReversed(false);

    TrajectoryConfig centripAccelConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
            .setKinematics(KINEMATICS)
            .addConstraint(voltageConstraint)
            .addConstraint(centripetalAccelConstraint)
            .setReversed(false);

    TrajectoryConfig slowConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
            .setKinematics(KINEMATICS)
            .addConstraint(slowVoltageConstraint)
            .setReversed(false);

    TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
            .setKinematics(KINEMATICS)
            .addConstraint(voltageConstraint)
            .setReversed(true);

    SimpleMotorFeedforward realisticFeedforward = new SimpleMotorFeedforward(KS, KV, KA);
    static SimpleMotorFeedforward getRealisticFeedforward() {return realisticFeedforward;}
    SimpleMotorFeedforward tunedFeedforward = new SimpleMotorFeedforward(KS_SLOW, KV_SLOW, KA_SLOW);
    static SimpleMotorFeedforward getTunedFeedforward() {return tunedFeedforward;}
    PIDController leftController = new PIDController(KP_VEL, 0, 0);
    PIDController rightController = new PIDController(KP_VEL, 0, 0);
    static RamseteCommand baseRamsete(Trajectory trajectory, SimpleMotorFeedforward simpleMotorFeedforward, Drivetrain drivetrain) {

        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                new RamseteController(KB, KZ),
                simpleMotorFeedforward,
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

    static RamseteCommand OutsideCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_GAME_PIECE_FORWARD), config), realisticFeedforward, drivetrain);
    }

    static RamseteCommand OutsideGamePieceToCommunity(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_BACKWARD, OUTSIDE_COMMUNITY_START), config), realisticFeedforward, drivetrain);
    }

    static RamseteCommand OutsideGamePieceToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_BACKWARD, OUTSIDE_CHARGING_STATION_FAR), slowConfig), tunedFeedforward, drivetrain);
    }

    static RamseteCommand OutsideCommunityToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_CHARGING_STATION_COMMUNITY), slowConfig), tunedFeedforward, drivetrain);
    }

    static RamseteCommand BarrierCommunityToGamePiece(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_GAME_PIECE_FORWARD), config), realisticFeedforward, drivetrain);
    }

    static RamseteCommand BarrierGamePieceToCommunity(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_BACKWARD, BARRIER_COMMUNITY_START), config), realisticFeedforward, drivetrain);
    }

    static RamseteCommand BarrierGamePieceToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_BACKWARD, BARRIER_CHARGING_STATION_FAR), slowConfig), tunedFeedforward, drivetrain);
    }

    static RamseteCommand BarrierScoreToChargingStation(Drivetrain drivetrain) {
        return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_SCORE, BARRIER_CHARGING_STATION_COMMUNITY), slowConfig), tunedFeedforward, drivetrain);
    }
}