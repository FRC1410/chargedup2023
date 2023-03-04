package org.frc1410.chargedup2023.subsystems;

import com.kauailabs.navx2.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc1410.chargedup2023.util.Constants;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.framework.util.log.Logger;

import static org.frc1410.chargedup2023.util.IDs.*;
import static org.frc1410.chargedup2023.util.Constants.*;

public class Drivetrain implements TickedSubsystem {

	private static final Logger log = new Logger("Drivetrain");

	// NetworkTables entries
	private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Drivetrain");
	private final DoublePublisher headingPub = NetworkTables.PublisherFactory(table, "Heading", 0);
	private final DoublePublisher pitchPub = NetworkTables.PublisherFactory(table, "Pitch", 0);
	private final DoublePublisher xPub = NetworkTables.PublisherFactory(table, "X", 0);
	private final DoublePublisher yPub = NetworkTables.PublisherFactory(table, "Y", 0);
	private final DoublePublisher voltagePub = NetworkTables.PublisherFactory(table, "Voltage", 0);
	private final DoublePublisher leftPub = NetworkTables.PublisherFactory(table, "Left Side", 0);
	private final DoublePublisher rightPub = NetworkTables.PublisherFactory(table, "Right Side", 0);
	private final DoublePublisher leftSpeedPub = NetworkTables.PublisherFactory(table, "Left Speed Side", 0);
	private final DoublePublisher rightSpeedPub = NetworkTables.PublisherFactory(table, "Right Speed Side", 0);
	private final StringPublisher selectionPub = NetworkTables.PublisherFactory(table, "Scoring Pose", "");

	private final CANSparkMax leftLeader = new CANSparkMax(DRIVETRAIN_LEFT_FRONT_MOTOR_ID, MotorType.kBrushless);
	private final CANSparkMax leftFollower = new CANSparkMax(DRIVETRAIN_LEFT_BACK_MOTOR_ID, MotorType.kBrushless);
	private final CANSparkMax rightLeader = new CANSparkMax(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID, MotorType.kBrushless);
	private final CANSparkMax rightFollower = new CANSparkMax(DRIVETRAIN_RIGHT_BACK_MOTOR_ID, MotorType.kBrushless);

	public final AHRS gyro = new AHRS(SPI.Port.kMXP, (byte)50);

	private final DifferentialDrive drive;

	private boolean hasBeenReset = false;

	private final DifferentialDrivePoseEstimator poseEstimator = new DifferentialDrivePoseEstimator(KINEMATICS,
			new Rotation2d(), 0, 0, new Pose2d());

	public Drivetrain() {
		initMotor(leftLeader);
		initMotor(leftFollower);
		initMotor(rightLeader);
		initMotor(leftFollower);

		leftFollower.follow(leftLeader);
		rightFollower.follow(rightLeader);

		leftLeader.setInverted(true);
		leftFollower.setInverted(true);
		rightLeader.setInverted(false);
		rightFollower.setInverted(false);

		drive = new DifferentialDrive(leftLeader, rightLeader);

		gyro.calibrate();
	}

	private void initMotor(CANSparkMax motor) {
		motor.restoreFactoryDefaults();
		motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	@Override
	public void periodic() {
		poseEstimator.update(
				new Rotation2d(Units.degreesToRadians(getHeading())),
				(leftLeader.getEncoder().getPosition() + leftFollower.getEncoder().getPosition()) / 2 * DRIVETRAIN_ENCODER_CONSTANT,
				(rightLeader.getEncoder().getPosition() + rightFollower.getEncoder().getPosition()) / 2 * DRIVETRAIN_ENCODER_CONSTANT
		);
		drive.feed();

		// NetworkTables updating
		headingPub.set(poseEstimator.getEstimatedPosition().getRotation().getDegrees());
		pitchPub.set(getPitch());
		xPub.set(Units.metersToInches(poseEstimator.getEstimatedPosition().getX()));
		yPub.set(Units.metersToInches(poseEstimator.getEstimatedPosition().getY()));
		voltagePub.set(RobotController.getBatteryVoltage());
		selectionPub.set(ScoringPosition.targetPosition.name());

		leftPub.set(leftLeader.getEncoder().getPosition());
		leftSpeedPub.set(leftLeader.getEncoder().getVelocity());
		rightPub.set(rightLeader.getEncoder().getPosition());
		rightSpeedPub.set(rightLeader.getEncoder().getVelocity());
	}

	public void triggerTankDrive(double left, double right, double triggerForwards, double triggerBackwards) {
		if (triggerForwards == 0 && triggerBackwards == 0) {
			teleopTankDriveVolts((-left * 12), (-right * 12));
		} else {
			double triggerValue = (-triggerForwards * 0.75) + (triggerBackwards * 0.75);
			double leftValue = (triggerValue + (-left * 0.25)) * 12;
			double rightValue = (triggerValue + (-right * 0.25)) * 12;
			
			teleopTankDriveVolts(leftValue, rightValue);
		}
	}

	public void teleopTankDriveVolts(double leftVolts, double rightVolts) {
		leftLeader.setVoltage(leftVolts);
		rightLeader.setVoltage(rightVolts);

		drive.feed();
	}

	public void autoTankDriveVolts(double leftVolts, double rightVolts) {
		leftLeader.setVoltage(leftVolts);
		rightLeader.setVoltage(rightVolts);

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
		poseEstimator.addVisionMeasurement(pose, timestamp, Constants.VISION_STD_DEVS);
//		log.debug("Vision pose added to pose estimator");
	}

	public void resetPoseEstimation(Pose2d pose) {
		leftLeader.getEncoder().setPosition(0);
		leftFollower.getEncoder().setPosition(0);
		rightLeader.getEncoder().setPosition(0);
		rightFollower.getEncoder().setPosition(0);

		poseEstimator.resetPosition(gyro.getRotation2d(), 0, 0, pose);

		log.debug("Pose Estimation Reset to: " + pose);
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		double leftEncoderVelocity = leftLeader.getEncoder().getVelocity() / 60 * DRIVETRAIN_ENCODER_CONSTANT;
		double rightEncoderVelocity = rightLeader.getEncoder().getVelocity() / 60 * DRIVETRAIN_ENCODER_CONSTANT;

		return new DifferentialDriveWheelSpeeds(leftEncoderVelocity, rightEncoderVelocity);
	}

	public double getVelocity() {
		double leftEncoderVelocity = leftLeader.getEncoder().getVelocity() / 60 * DRIVETRAIN_ENCODER_CONSTANT;
		double rightEncoderVelocity = rightLeader.getEncoder().getVelocity() / 60 * DRIVETRAIN_ENCODER_CONSTANT;

		return (leftEncoderVelocity + rightEncoderVelocity) / 2;
	}

	public void coastMode() {
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		leftFollower.setIdleMode(CANSparkMax.IdleMode.kCoast);
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		rightFollower.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}

	public void brakeMode() {
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
		leftFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightFollower.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void zeroHeading() {
		gyro.reset();
		log.debug("zeroHeading called. Gyro reset");
	}

	public double getHeading() {
		return gyro.getRotation2d().getDegrees();
//		double angle = gyro.getAngle() % 360;
//		if (angle > 180) {
//			return angle - 360;
//		} else if (angle < -180) {
//			return angle + 360;
//		}
//		return angle;
	}

	public double getPitch() {
		return gyro.getRoll() + 2;
	}

	public boolean hasBeenReset() {
		return hasBeenReset;
	}

	public void setReset(boolean hasBeenReset) {
		this.hasBeenReset = hasBeenReset;
	}
}