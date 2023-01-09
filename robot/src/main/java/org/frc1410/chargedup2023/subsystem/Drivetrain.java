package org.frc1410.chargedup2023.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.IDs.*;

public class Drivetrain implements TickedSubsystem {
    public final WPI_TalonFX leftLeader = new WPI_TalonFX(DRIVETRAIN_LEFT_FRONT_MOTOR_ID);
    public final WPI_TalonFX leftFollower = new WPI_TalonFX(DRIVETRAIN_LEFT_BACK_MOTOR_ID);
    public final WPI_TalonFX rightLeader = new WPI_TalonFX(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID);
    public final WPI_TalonFX rightFollower = new WPI_TalonFX(DRIVETRAIN_RIGHT_BACK_MOTOR_ID);


    private final DifferentialDrive drive;

    private boolean isInverted = false;

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

    }

    public void tankDrive(double left, double right, boolean squared) {
        if (isInverted) {
            drive.tankDrive(-right, -left, squared);
        } else {
            drive.tankDrive(left, right, squared);
        }
    }

    public void flip() {
        isInverted = !isInverted;
    }
}
