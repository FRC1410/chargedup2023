package org.frc1410.chargedup2023.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

public class Drivetrain implements TickedSubsystem {
    public final WPI_TalonFX leftLeader = new WPI_TalonFX(DRIVETRAIN_LEFT_FRONT_MOTOR_ID);
    public final WPI_TalonFX leftFollower = new WPI_TalonFX(DRIVETRAIN_LEFT_BACK_MOTOR_ID);
    public final WPI_TalonFX rightLeader = new WPI_TalonFX(DRIVETRAIN_RIGHT_FRONT_MOTOR_ID);
    public final WPI_TalonFX rightFollower = new WPI_TalonFX(DRIVETRAIN_RIGHT_BACK_MOTOR_ID);
    @Override
    public void periodic() {

    }
}
