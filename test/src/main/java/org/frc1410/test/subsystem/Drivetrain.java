package org.frc1410.test.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.test.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.test.util.IDs.*;
import static org.frc1410.test.util.Constants.*;

public class Drivetrain implements TickedSubsystem, Subsystem {
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
    private boolean isInverted = false;
    private boolean isArcadeDrive = true;

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
                new Rotation2d(Units.degreesToRadians(gyro.getAngle() % 360)),
                leftLeader.getSelectedSensorPosition() * ENCODER_CONSTANT,
                rightLeader.getSelectedSensorPosition() * ENCODER_CONSTANT
        );
        drive.feed();

        // NetworkTables updating
        gyroPub.set(gyro.getAngle() % 360);
        headingPub.set(poseEstimator.getEstimatedPosition().getRotation().getDegrees() % 360);
        xPub.set(poseEstimator.getEstimatedPosition().getX());
        yPub.set(poseEstimator.getEstimatedPosition().getY());
        voltagePub.set(RobotController.getBatteryVoltage());
    }

    public boolean getDriveMode() {return isArcadeDrive;}

    public void tankDrive(double left, double right, boolean squared) {
        if (isInverted) {
            drive.tankDrive(-left, -right, squared);
        } else {
            drive.tankDrive(right, left, squared);
        }
    }

    public void triggerTankDrive(double left, double right, double triggerForwards, double triggerBackwards) {
        if (triggerForwards== 0 && triggerBackwards== 0) {
            tankDriveVolts((left* 12), (right* 12));
        } else {
            double triggerValue = (triggerForwards* 0.75) + (-triggerBackwards* 0.75);
            double leftValue = (triggerValue + (left* 0.25)) * 12;
            double rightValue = (triggerValue + (right* 0.25)) * 12;
            tankDriveVolts(leftValue, rightValue);
        }
    }

    public void arcadeDrive(double speed, double rotation, boolean squared) {
        // If rotation value is positive, the rotation is counter-clockwise
        if (isInverted) {
            drive.arcadeDrive(-speed, -rotation, squared);
        } else {
            drive.arcadeDrive(speed, -rotation, squared);
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

    public void switchDriveMode() {
        isArcadeDrive = !isArcadeDrive;
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
}
