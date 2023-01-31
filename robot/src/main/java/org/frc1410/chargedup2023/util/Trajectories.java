package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
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

    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .addConstraint(voltageConstraint)
        .setReversed(false);

	TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
		.setKinematics(KINEMATICS)
		.addConstraint(voltageConstraint)
		.setReversed(true);

    TrajectoryConfig slowConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .addConstraint(slowVoltageConstraint)
        .setReversed(false);

	TrajectoryConfig slowReverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
		.setKinematics(KINEMATICS)
		.addConstraint(slowVoltageConstraint)
		.setReversed(true);

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
	static RamseteCommand BarrierMobility(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_COMMUNITY_EXIT),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}

	static RamseteCommand OutsideMobility(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_COMMUNITY_EXIT),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}


	static RamseteCommand BarrierCommunityToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_CHARGING_STATION_COMMUNITY),
				slowConfig), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}

	static RamseteCommand OutsideCommunityToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_CHARGING_STATION_COMMUNITY),
				slowConfig), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}


	static RamseteCommand BarrierCommunityToGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_START, BARRIER_GAME_PIECE_FORWARD),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}

	static RamseteCommand OutsideCommunityToGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_START, OUTSIDE_GAME_PIECE_FORWARD),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}


	static RamseteCommand BarrierGamePieceToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_BACKWARD, BARRIER_CHARGING_STATION_FAR),
				slowConfig), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}
	// FOR GAME PIECE TO CHARGING STATION, BACKWARD IS USED BECAUSE THE ROBOT DOESN'T NEED TO HAVE ELEVATOR FACING SCORING
	static RamseteCommand OutsideGamePieceToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_BACKWARD, OUTSIDE_CHARGING_STATION_FAR),
				slowConfig), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}


	static RamseteCommand BarrierGamePieceToScore(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_GAME_PIECE_FORWARD, BARRIER_COMMUNITY_SCORE),
				reverseConfig), realisticFeedforward, leftController, rightController, drivetrain);
	}
	// FOR GAME PIECE TO SCORE, FORWARD AND REVERSED IS USED BECAUSE THE ROBOT SHOULD END WITH ELEVATOR FACING SCORING NODE
	static RamseteCommand OutsideGamePieceToScore(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_GAME_PIECE_FORWARD, OUTSIDE_COMMUNITY_SCORE),
				reverseConfig), realisticFeedforward, leftController, rightController, drivetrain);
	}


	static RamseteCommand BarrierScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_SCORE, BARRIER_CHARGING_STATION_COMMUNITY),
				config), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}
	static RamseteCommand OutsideScoreToChargingStation(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_SCORE, OUTSIDE_CHARGING_STATION_COMMUNITY),
				config), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);
	}


	static RamseteCommand BarrierScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(BARRIER_COMMUNITY_SCORE, BARRIER_MIDDLE_GAME_PIECE_FORWARD),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}

	static RamseteCommand OutsideScoreToMiddleGamePiece(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(OUTSIDE_COMMUNITY_SCORE, OUTSIDE_MIDDLE_GAME_PIECE_FORWARD),
				config), realisticFeedforward, leftController, rightController, drivetrain);
	}
}