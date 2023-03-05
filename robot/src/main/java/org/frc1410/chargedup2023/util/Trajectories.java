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
            new SimpleMotorFeedforward(KS, KV, KA), KINEMATICS, 4);

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
					drivetrain.autoTankDriveVolts(leftVolts, rightVolts);

					leftMeasurementPub.set(drivetrain.getWheelSpeeds().leftMetersPerSecond);
					leftReferencePub.set(leftController.getSetpoint());

					rightMeasurementPub.set(drivetrain.getWheelSpeeds().rightMetersPerSecond);
					rightReferencePub.set(rightController.getSetpoint());
					instance.flush();
				}
		);
    }

	// TRAJECTORIES
	static SequentialCommandGroup BarrierGridToOklahoma(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				BARRIER_GRID, List.of(OKLAHOMA_MIDPOINT, OKLAHOMA_MIDPOINT2), OKLAHOMA,
				reverseConfigCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.autoTankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OklahomaToScorePapa(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				OKLAHOMA, List.of(OKLAHOMA_G302), BARRIER_SCORE_PAPA,
				configCentripAccel), realisticFeedforward, leftController, rightController, drivetrain)
				.andThen(() -> drivetrain.autoTankDriveVolts(0, 0));
	}
}