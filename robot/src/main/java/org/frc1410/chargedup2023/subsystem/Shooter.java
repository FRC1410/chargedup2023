package org.frc1410.chargedup2023.subsystem;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

public class Shooter implements TickedSubsystem, Subsystem {
    private final WPI_TalonFX motor = new WPI_TalonFX(30);

    public Shooter() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }
}

