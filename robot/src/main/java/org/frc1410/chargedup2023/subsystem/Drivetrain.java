package org.frc1410.chargedup2023.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.chargedup2023.util.Networktables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.*;
import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;

public class Drivetrain implements TickedSubsystem, Subsystem {
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Drivetrain");
    DoublePublisher headingPub = Networktables.PublisherFactory(table, "Heading", 0);
    DoublePublisher xPub = Networktables.PublisherFactory(table, "X", 0);
    DoublePublisher yPub = Networktables.PublisherFactory(table, "Y", 0);

    // Motors
    public final WPI_TalonFX leftLeader = new WPI_TalonFX(DRIVETRAIN_LEFT_FRONT_MOTOR_ID);
    public final WPI_TalonFX leftFollower = new WPI_TalonFX(DRIVETRAIN_LEFT_BACK_MOTOR_ID);
    public final WPI_TalonFX rightLeader = new WPI_TalonFX(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID);
    public final WPI_TalonFX rightFollower = new WPI_TalonFX(DRIVETRAIN_RIGHT_BACK_MOTOR_ID);

    // Simulation
    public DifferentialDrivetrainSim drivetrainSimulator;
    public Field2d fieldSim;

    public final Encoder rightEncoder = new Encoder(RIGHT_ENCODER_PORTS[0], RIGHT_ENCODER_PORTS[1], RIGHT_ENCODER_REVERSED);
    public final Encoder leftEncoder = new Encoder(LEFT_ENCODER_PORTS[0], LEFT_ENCODER_PORTS[1], LEFT_ENCODER_REVERSED);

    public EncoderSim leftEncoderSim;
    public EncoderSim rightEncoderSim;

    // Gyro & Simulated Gyro
    public final AHRS gyro = new AHRS(SPI.Port.kMXP);
    public int dev;
    public SimDouble angle;

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

        if (RobotBase.isSimulation()) simulationInit();
    }

    public void simulationInit() {
        drivetrainSimulator = new DifferentialDrivetrainSim(
                DRIVETRAIN_PLANT, DCMotor.getFalcon500(2), GEARING, TRACKWIDTH, WHEEL_DIAMETER, NOISE);

//        leftLeader.setInverted(false);
        leftEncoder.setDistancePerPulse(ENCODER_DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(ENCODER_DISTANCE_PER_PULSE);
        leftEncoderSim = new EncoderSim(leftEncoder);
        rightEncoderSim = new EncoderSim(rightEncoder);
        dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
        angle = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw"));
        fieldSim = new Field2d();
        SmartDashboard.putData("Field", fieldSim);
    }

    private void initFalcon(WPI_TalonFX falcon) {
        falcon.configFactoryDefault();
        falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        falcon.configNeutralDeadband(0.001);
        falcon.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void periodic() {
        if (RobotBase.isSimulation()) {
            drivetrainSimulator.setInputs(
                    leftLeader.get() * RobotController.getBatteryVoltage(),
                    rightLeader.get() * RobotController.getBatteryVoltage());
            drivetrainSimulator.update(20.0 / 1000.0);

            angle.set(-drivetrainSimulator.getHeading().getDegrees());

            leftEncoderSim.setDistance(drivetrainSimulator.getLeftPositionMeters());
            leftEncoderSim.setRate(drivetrainSimulator.getLeftVelocityMetersPerSecond());
            rightEncoderSim.setDistance(drivetrainSimulator.getRightPositionMeters());
            rightEncoderSim.setRate(drivetrainSimulator.getRightVelocityMetersPerSecond());

            Rotation2d heading = new Rotation2d(-Units.degreesToRadians(drivetrainSimulator.getHeading().getDegrees()));
            poseEstimator.update(heading, leftEncoder.getDistance(), rightEncoder.getDistance());
            fieldSim.setRobotPose(getPoseEstimation());
        } else {
            poseEstimator.update(
                    new Rotation2d(Units.degreesToRadians(gyro.getAngle() % 360)),
                    leftLeader.getSelectedSensorPosition() * ENCODER_CONSTANT,
                    rightLeader.getSelectedSensorPosition() * ENCODER_CONSTANT);
            drive.feed();
        }

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

    public void resetEncoders() {
        if (RobotBase.isSimulation()) {leftEncoder.reset(); rightEncoder.reset();}
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);
    }

    public void resetPoseEstimation(Pose2d pose) {
        resetEncoders(); // Might be unnecessary
        if (RobotBase.isSimulation()) {
            drivetrainSimulator.setPose(pose);
            poseEstimator.resetPosition(pose.getRotation(), leftEncoder.getDistance(), rightEncoder.getDistance(), pose);
        } else poseEstimator.resetPosition(gyro.getRotation2d(), 0, 0, pose);
        // Consider passing in encoders to this method ^ to not have to reset encoders themselves

    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        double leftEncoderVelocity = leftLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;
        double rightEncoderVelocity = rightLeader.getSelectedSensorVelocity() * ENCODER_CONSTANT * 10;

        if (RobotBase.isSimulation()) {return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());}
        else {return new DifferentialDriveWheelSpeeds(leftEncoderVelocity, rightEncoderVelocity);}
    }

    public void flip() {
        isInverted = !isInverted;
    }

    public void switchDriveMode() {
        isArcadeDrive = !isArcadeDrive;
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
