package org.frc1410.chargedup2023.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.*;
import static org.frc1410.chargedup2023.util.Constants.*;

public class Drivetrain implements TickedSubsystem {

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

        drive = new DifferentialDrive(leftLeader, rightLeader);
    }

    private void initFalcon(WPI_TalonFX falcon) {
        falcon.configFactoryDefault();
        falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        falcon.configNeutralDeadband(0.001);
    }

    @Override
    public void periodic() {
        poseEstimator.update(
                new Rotation2d(gyro.getAngle()),
                leftLeader.getSelectedSensorPosition() * ENCODER_CONSTANT,
                rightLeader.getSelectedSensorPosition() * ENCODER_CONSTANT
        );
    }

    public void tankDrive(double left, double right, boolean squared) {
        if (isInverted) {
            drive.tankDrive(-right, -left, squared);
        } else {
            drive.tankDrive(left, right, squared);
        }
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        leftLeader.setVoltage(leftVolts);
        rightLeader.setVoltage(rightVolts);
        drive.feed();
    }

    public Pose2d getPoseEstimation() {
        return poseEstimator.getEstimatedPosition();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        double leftEncoderVelocity = leftLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;
        double rightEncoderVelocity = rightLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;
        return new DifferentialDriveWheelSpeeds(leftEncoderVelocity, rightEncoderVelocity);
    }

    public void flip() {
        isInverted = !isInverted;
    }

    public void zeroHeading() {
        gyro.reset();
    }
}
