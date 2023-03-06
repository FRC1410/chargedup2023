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

	DifferentialDriveVoltageConstraint voltageConstraintAuto = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(KS_AUTO, KV_AUTO, KA_AUTO), KINEMATICS, 5);

	DifferentialDriveVoltageConstraint voltageConstraintTeleop = new DifferentialDriveVoltageConstraint(
			new SimpleMotorFeedforward(KS_TELEOP, KV_TELEOP, KA_TELEOP), KINEMATICS, 6);

	CentripetalAccelerationConstraint centripAccelConstraintAuto = new CentripetalAccelerationConstraint(2.4);
	CentripetalAccelerationConstraint centripAccelConstraintTeleop = new CentripetalAccelerationConstraint(2.4);

	TrajectoryConfig configCentripAccel = new TrajectoryConfig(MAX_SPEED_AUTO, MAX_ACCEL_AUTO)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraintAuto)
			.setReversed(false)
			.addConstraint(centripAccelConstraintAuto);

	TrajectoryConfig reverseConfigCentripAccel = new TrajectoryConfig(MAX_SPEED_AUTO, MAX_ACCEL_AUTO)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraintAuto)
			.setReversed(true)
			.addConstraint(centripAccelConstraintAuto);

	TrajectoryConfig configCentripAccelOTF = new TrajectoryConfig(MAX_SPEED_TELEOP, MAX_ACCEL_TELEOP)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraintTeleop)
			.setReversed(false)
			.addConstraint(centripAccelConstraintTeleop);
//			.setStartVelocity(0);

	TrajectoryConfig reverseConfigCentripAccelOTF = new TrajectoryConfig(MAX_SPEED_TELEOP, MAX_ACCEL_TELEOP)
			.setKinematics(KINEMATICS)
			.addConstraint(voltageConstraintTeleop)
			.setReversed(true)
			.addConstraint(centripAccelConstraintTeleop);
//			.setStartVelocity(0);

    SimpleMotorFeedforward feedForwardAuto = new SimpleMotorFeedforward(KS_AUTO, KV_AUTO, KA_AUTO);
	SimpleMotorFeedforward feedForwardTeleop = new SimpleMotorFeedforward(KS_TELEOP, KV_TELEOP, KA_TELEOP);

	PIDController leftControllerAuto = new PIDController(KP_VEL_AUTO, 0, 0);
    PIDController rightControllerAuto = new PIDController(KP_VEL_AUTO, 0, 0);

	PIDController leftControllerTeleop = new PIDController(KP_VEL_TELEOP, 0, 0);
	PIDController rightControllerTeleop = new PIDController(KP_VEL_TELEOP, 0, 0);

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
//					instance.flush();
				}
		);
    }

	// TRAJECTORIES
	static SequentialCommandGroup BarrierGridToOklahoma(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				BARRIER_GRID, List.of(OKLAHOMA_MIDPOINT, OKLAHOMA_MIDPOINT2), OKLAHOMA,
				reverseConfigCentripAccel), feedForwardAuto, leftControllerAuto, rightControllerAuto, drivetrain)
				.andThen(() -> drivetrain.autoTankDriveVolts(0, 0));
	}

	static SequentialCommandGroup OklahomaToScorePapa(Drivetrain drivetrain) {
		return baseRamsete(TrajectoryGenerator.generateTrajectory(
				OKLAHOMA, List.of(OKLAHOMA_G302), BARRIER_SCORE_PAPA,
				configCentripAccel), feedForwardAuto, leftControllerAuto, rightControllerAuto, drivetrain)
				.andThen(() -> drivetrain.autoTankDriveVolts(0, 0));
	}
}