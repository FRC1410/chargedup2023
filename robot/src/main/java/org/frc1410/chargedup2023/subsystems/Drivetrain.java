package org.frc1410.chargedup2023.subsystems;

import com.kauailabs.navx.frc.AHRS;
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
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.*;
import static org.frc1410.chargedup2023.util.Constants.*;

public class Drivetrain implements TickedSubsystem {
    // NetworkTables entries
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Drivetrain");
    DoublePublisher headingPub = NetworkTables.PublisherFactory(table, "Heading", 0);
    DoublePublisher xPub = NetworkTables.PublisherFactory(table, "X", 0);
    DoublePublisher yPub = NetworkTables.PublisherFactory(table, "Y", 0);

    private final CANSparkMax leftLeader = new CANSparkMax(DRIVETRAIN_LEFT_FRONT_MOTOR_ID, MotorType.kBrushless);
    private final CANSparkMax leftFollower = new CANSparkMax(DRIVETRAIN_LEFT_BACK_MOTOR_ID, MotorType.kBrushless);
    private final CANSparkMax rightLeader = new CANSparkMax(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID, MotorType.kBrushless);
    private final CANSparkMax rightFollower = new CANSparkMax(DRIVETRAIN_RIGHT_BACK_MOTOR_ID, MotorType.kBrushless);

    public final AHRS gyro = new AHRS(SPI.Port.kMXP);

    private final DifferentialDrive drive;

    private boolean isInverted = false;
    private boolean isArcadeDrive = true;

    private final DifferentialDrivePoseEstimator poseEstimator = new DifferentialDrivePoseEstimator(KINEMATICS,
            new Rotation2d(), 0., 0., new Pose2d());

    public Drivetrain() {
        initMotor(leftLeader);
        initMotor(leftFollower);
        initMotor(rightLeader);
        initMotor(leftFollower);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftLeader.setInverted(true);
        leftFollower.setInverted(true);

        drive = new DifferentialDrive(leftLeader, rightLeader);

        gyro.calibrate();
    }

    private void initMotor(CANSparkMax motor) {
        motor.restoreFactoryDefaults();
        motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    @Override
    public void periodic() {
        poseEstimator.update(
                new Rotation2d(Units.degreesToRadians(gyro.getAngle() % 360)),
                (leftLeader.getEncoder().getPosition() + leftFollower.getEncoder().getPosition())/2 * METERS_PER_REVOLUTION,
                (rightLeader.getEncoder().getPosition() + rightFollower.getEncoder().getPosition())/2 * METERS_PER_REVOLUTION
        );
        drive.feed();

        // NetworkTables updating
        headingPub.set(gyro.getAngle() % 360);
        xPub.set(poseEstimator.getEstimatedPosition().getX());
        yPub.set(poseEstimator.getEstimatedPosition().getY());
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
        if (triggerForwards == 0 && triggerBackwards == 0) {
            tankDriveVolts((left * 12), (right * 12));
        } else {
            double triggerValue = (triggerForwards * 0.75) + (-triggerBackwards * 0.75);
            double leftValue = (triggerValue + (left * 0.25)) * 12;
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
        initMotor(leftLeader);
        initMotor(leftFollower);
        initMotor(rightLeader);
        initMotor(leftFollower);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftLeader.setInverted(true);
        leftFollower.setInverted(true);

        poseEstimator.resetPosition(gyro.getRotation2d(), 0, 0, pose);
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        double leftEncoderVelocity = leftLeader.getEncoder().getVelocity();
        double rightEncoderVelocity = rightLeader.getEncoder().getVelocity();
        return new DifferentialDriveWheelSpeeds(leftEncoderVelocity, rightEncoderVelocity);
    }

    public void flip() {
        isInverted = !isInverted;
    }

    public void switchDriveMode() {
        isArcadeDrive = !isArcadeDrive;
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
    }
}