package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
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

	DifferentialDriveVoltageConstraint voltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 11);

    DifferentialDriveVoltageConstraint slowVoltageConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS_SLOW, KV_SLOW, KA_SLOW), KINEMATICS, 5);

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

    SimpleMotorFeedforward realisticFeedforward = new SimpleMotorFeedforward(KS, KV, KA);
	SimpleMotorFeedforward tunedFeedforward = new SimpleMotorFeedforward(KS_SLOW, KV_SLOW, KA_SLOW);

    static SimpleMotorFeedforward getRealisticFeedforward() {return realisticFeedforward;}
    static SimpleMotorFeedforward getTunedFeedforward() {return tunedFeedforward;}

    PIDController leftController = new PIDController(KP_VEL, 0, 0);
    PIDController rightController = new PIDController(KP_VEL, 0, 0);

	PIDController leftControllerSlow = new PIDController(KP_VEL_SLOW, 0, 0);
	PIDController rightControllerSlow = new PIDController(KP_VEL_SLOW, 0, 0);

    static RamseteCommand baseRamsete(Trajectory trajectory, SimpleMotorFeedforward simpleMotorFeedforward,
									  PIDController leftController, PIDController rightController, Drivetrain drivetrain) {

        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                new RamseteController(KB, KZ),
                simpleMotorFeedforward,
                KINEMATICS,
                drivetrain::getWheelSpeeds,
                leftController,
                rightController,
                drivetrain::tankDriveVolts
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
				configCentripAccel), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_SCORE, BARRIER_CHARGING_STATION_COMMUNITY),
				configCentripAccel), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_SCORE, OUTSIDE_CHARGING_STATION_COMMUNITY),
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(BARRIER_SCORE_CONE_ANGLED, List.of(BARRIER_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT), BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD,
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(OUTSIDE_SCORE_CONE_ANGLED, List.of(OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_MIDPOINT), OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD,
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


	static SequentialCommandGroup BarrierMiddleGamePieceToScoreCube(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				BARRIER_MIDDLE_GAME_PIECE_ANGLED_FORWARD, List.of(BARRIER_CUBE_SCORE_MIDPOINT), BARRIER_COMMUNITY_SCORE_CUBE,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideMiddleGamePieceToScoreCube(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				OUTSIDE_MIDDLE_GAME_PIECE_ANGLED_FORWARD, List.of(OUTSIDE_CUBE_SCORE_MIDPOINT), OUTSIDE_COMMUNITY_SCORE_CUBE,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}


	static SequentialCommandGroup BarrierGamePieceToScoreNuclear(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_SCORE_CONE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OutsideGamePieceToScoreNuclear(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_FORWARD, OUTSIDE_SCORE_CONE_ANGLED),
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.tankDriveVolts(0, 0));
	}
}