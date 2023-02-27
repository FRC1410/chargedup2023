package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import java.util.List;

import static org.frc1410.chargedup2023.auto.POIs.*;
import static org.frc1410.chargedup2023.util.Constants.KINEMATICS;
import static org.frc1410.chargedup2023.util.Tuning.KZ;
import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;

public interface Trajectories {

	NetworkTableInstance instance = NetworkTableInstance.getDefault();
	NetworkTable table = instance.getTable("trajectories");
	DoublePublisher leftMeasurementPub = NetworkTables.PublisherFactory(table, "Left Measurement", 0);
	DoublePublisher leftReferencePub = NetworkTables.PublisherFactory(table, "Left Desired", 0);
	DoublePublisher rightMeasurementPub = NetworkTables.PublisherFactory(table, "Right Measurement", 0);
	DoublePublisher rightReferencePub = NetworkTables.PublisherFactory(table, "Right Desired", 0);

	DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 11);

    DifferentialDriveVoltageConstraint slowVoltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 6);

	CentripetalAccelerationConstraint centripAccelConstraint = new CentripetalAccelerationConstraint(2.4);

	TrajectoryConfig slowConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(slowVoltageConstraint)
			.setReversed(false);

	TrajectoryConfig slowReverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(slowVoltageConstraint)
			.setReversed(true);

	TrajectoryConfig configCentripAccel = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraint)
			.setReversed(false)
			.addConstraint(centripAccelConstraint);

	TrajectoryConfig reverseConfigCentripAccel = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraint)
			.setReversed(true)
			.addConstraint(centripAccelConstraint);

	TrajectoryConfig configCentripAccelOTF = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(slowVoltageConstraint)
			.setReversed(false)
			.addConstraint(centripAccelConstraint)
			.setStartVelocity(0);

	TrajectoryConfig reverseConfigCentripAccelOTF = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
			.setKinematics(KINEMATICS)
			.addConstraint(slowVoltageConstraint)
			.setReversed(true)
			.addConstraint(centripAccelConstraint)
			.setStartVelocity(0);

    SimpleMotorFeedforward realisticFeedforward = new SimpleMotorFeedforward(KS, KV, KA);

    static SimpleMotorFeedforward getRealisticFeedforward() {return realisticFeedforward;}

    PIDController leftController = new PIDController(KP_VEL, 0, 0);
    PIDController rightController = new PIDController(KP_VEL, 0, 0);

	RamseteController ramseteController = new RamseteController(KB, KZ);

    static RamseteCommand baseRamsete(Trajectory trajectory, SimpleMotorFeedforward simpleMotorFeedforward,
									  PIDController leftController, PIDController rightController, Drivetrain drivetrain) {
        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                ramseteController,
                simpleMotorFeedforward,
                KINEMATICS,
                drivetrain::getWheelSpeeds,
                leftController,
                rightController,
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

	// TRAJECTORIES
	static SequentialCommandGroup BarrierCommunityToGrid(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_COMMUNITY_GRID),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideCommunityToGrid(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_COMMUNITY_GRID),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGridToGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_GRID, BARRIER_GAME_PIECE_FORWARD_MIDPOINT),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGridToGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_GRID, OUTSIDE_GAME_PIECE_FORWARD_MIDPOINT),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGamePieceToIntake(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_BACKWARD_MIDPOINT, BARRIER_GAME_PIECE_BACKWARD),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGamePieceToIntake(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_BACKWARD_MIDPOINT, OUTSIDE_GAME_PIECE_BACKWARD),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGamePieceToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_BACKWARD, BARRIER_CHARGING_STATION_FAR),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGamePieceToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_BACKWARD, OUTSIDE_CHARGING_STATION_FAR),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_SCORE, BARRIER_CHARGING_STATION_COMMUNITY),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_SCORE, OUTSIDE_CHARGING_STATION_COMMUNITY),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(BARRIER_SCORE_YANKEE_ANGLED, List.of(BARRIER_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT), BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD,
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(OUTSIDE_SCORE_YANKEE_ANGLED, List.of(OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT), OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD,
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierMiddleGamePieceToIntake(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_MIDDLE_GAME_PIECE_ANGLED_BACKWARD, BARRIER_MIDDLE_GAME_PIECE_INTAKE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideMiddleGamePieceToIntake(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_BACKWARD, OUTSIDE_MIDDLE_GAME_PIECE_INTAKE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGamePieceToScore(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				BARRIER_GAME_PIECE_FORWARD, List.of(BARRIER_GAME_PIECE_SCORE_MIDPOINT), BARRIER_COMMUNITY_SCORE,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGamePieceToScore(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				OUTSIDE_GAME_PIECE_FORWARD, List.of(OUTSIDE_GAME_PIECE_SCORE_MIDPOINT), OUTSIDE_COMMUNITY_SCORE,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierMiddleGamePieceToScorePapa(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD, List.of(BARRIER_PAPA_SCORE_MIDPOINT), BARRIER_COMMUNITY_SCORE_PAPA,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideMiddleGamePieceToScorePapa(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD, List.of(OUTSIDE_PAPA_SCORE_MIDPOINT), OUTSIDE_COMMUNITY_SCORE_PAPA,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGamePieceToScoreAngled(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_SCORE_YANKEE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGamePieceToScoreAngled(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_FORWARD, OUTSIDE_SCORE_YANKEE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup Taxiiii(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(new Pose2d(0, 0, new Rotation2d()), new Pose2d(3 ,1, new Rotation2d())),
				slowConfig), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}
}