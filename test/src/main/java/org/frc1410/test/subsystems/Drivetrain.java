package org.frc1410.test.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc1410.test.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.test.util.IDs.*;
import static org.frc1410.test.util.Constants.*;

public class Drivetrain implements TickedSubsystem {

	NetworkTableInstance instance = NetworkTableInstance.getDefault();
	NetworkTable table = instance.getTable("Drivetrain");
	DoublePublisher headingPub = NetworkTables.PublisherFactory(table, "Heading", 0);
	DoublePublisher gyroPub = NetworkTables.PublisherFactory(table, "Gyro Yaw", 0);
	DoublePublisher xPub = NetworkTables.PublisherFactory(table, "X", 0);
	DoublePublisher yPub = NetworkTables.PublisherFactory(table, "Y", 0);
	DoublePublisher voltagePub = NetworkTables.PublisherFactory(table, "Voltage", 0);

	// Motors
	public final WPI_TalonFX leftLeader = new WPI_TalonFX(DRIVETRAIN_LEFT_FRONT_MOTOR_ID);
	public final WPI_TalonFX leftFollower = new WPI_TalonFX(DRIVETRAIN_LEFT_BACK_MOTOR_ID);
	public final WPI_TalonFX rightLeader = new WPI_TalonFX(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID);
	public final WPI_TalonFX rightFollower = new WPI_TalonFX(DRIVETRAIN_RIGHT_BACK_MOTOR_ID);

	// Gyro
	public final AHRS gyro = new AHRS(SPI.Port.kMXP);

	// Differential Drive for Teleop control
	private final DifferentialDrive drive;

	// Inverted flag for flipping driving direction in Teleop
	private boolean isInverted = true;

	public final DifferentialDrivePoseEstimator poseEstimator = new DifferentialDrivePoseEstimator(KINEMATICS,
			new Rotation2d(), 0., 0., new Pose2d());

	public Drivetrain() {
		initFalcon(leftLeader);
		initFalcon(leftFollower);
		initFalcon(rightLeader);
		initFalcon(leftFollower);

		leftFollower.follow(leftLeader);
		rightFollower.follow(rightLeader);

		leftLeader.setInverted(true);
		leftFollower.setInverted(InvertType.FollowMaster);

		drive = new DifferentialDrive(leftLeader, rightLeader);

		gyro.calibrate();
	}

	private void initFalcon(WPI_TalonFX falcon) {
		falcon.configFactoryDefault();
		falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
		falcon.configNeutralDeadband(0.001);
		falcon.setNeutralMode(NeutralMode.Brake);
	}

	@Override
	public void periodic() {
		poseEstimator.update(
				new Rotation2d(Units.degreesToRadians(gyro.getAngle())),
				leftLeader.getSelectedSensorPosition() * ENCODER_CONSTANT,
				rightLeader.getSelectedSensorPosition() * ENCODER_CONSTANT
		);
		drive.feed();

		gyroPub.set(gyro.getAngle());
		headingPub.set(poseEstimator.getEstimatedPosition().getRotation().getDegrees());
		xPub.set(Units.metersToInches(poseEstimator.getEstimatedPosition().getX()));
		yPub.set(Units.metersToInches(poseEstimator.getEstimatedPosition().getY()));
		voltagePub.set(RobotController.getBatteryVoltage());
	}

	public void triggerTankDrive(double left, double right, double triggerForwards, double triggerBackwards) {
		if (triggerForwards == 0 && triggerBackwards == 0) {
			tankDriveVolts((-right * 12), (-left * 12));
		} else {
			double triggerValue = (triggerForwards * 0.50) + (-triggerBackwards * 0.50);
			double leftValue = (triggerValue + (-right * 0.50)) * 12;
			double rightValue = (triggerValue + (-left * 0.50)) * 12;
			tankDriveVolts(leftValue, rightValue);
		}
	}

	public void tankDriveVolts(double leftVolts, double rightVolts) {
		// This is by design! (To match the functional tankDrive method)
		leftLeader.setVoltage(rightVolts);
		rightLeader.setVoltage(leftVolts);

		drive.feed();
	}

	public Pose2d getPoseEstimation() {
			return poseEstimator.getEstimatedPosition();
	}

	public void setEngagePower(double power) {
		leftLeader.set(power);
		rightLeader.set(power);
	}

	public void addVisionPose(Pose2d pose, double timestamp) {
		poseEstimator.addVisionMeasurement(pose, timestamp, new Matrix<>(VecBuilder.fill(0.3, 0.3, 1.0)));
	}

	public void resetPoseEstimation(Pose2d pose) {
		leftLeader.setSelectedSensorPosition(0);
		rightLeader.setSelectedSensorPosition(0);
		poseEstimator.resetPosition(gyro.getRotation2d(), 0, 0, pose);
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		double leftEncoderVelocity = leftLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;
		double rightEncoderVelocity = rightLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;
		return new DifferentialDriveWheelSpeeds(leftEncoderVelocity, rightEncoderVelocity);
	}

	public void flip() {
		isInverted = !isInverted;
	}

	public void resetFollowers() {
		initFalcon(leftFollower);
		initFalcon(rightFollower);
		leftFollower.follow(leftLeader);
		rightFollower.follow(rightLeader);
	}

	public void coastMode() {
		leftLeader.setNeutralMode(NeutralMode.Coast);
		leftFollower.setNeutralMode(NeutralMode.Coast);
		rightLeader.setNeutralMode(NeutralMode.Coast);
		rightFollower.setNeutralMode(NeutralMode.Coast);
	}

	public void brakeMode() {
		leftLeader.setNeutralMode(NeutralMode.Brake);
		leftFollower.setNeutralMode(NeutralMode.Brake);
		rightLeader.setNeutralMode(NeutralMode.Brake);
		rightFollower.setNeutralMode(NeutralMode.Brake);
	}

	public void zeroHeading() {
		gyro.reset();
	}

	public void zeroHeading(double angle) {
		gyro.reset();
		gyro.setAngleAdjustment(angle);
	}

	public double getHeading() {
		return gyro.getAngle();
	}

	public double getPitch() {
		return gyro.getPitch();
	}
}
