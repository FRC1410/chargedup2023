package org.frc1410.test.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class VerticalStorage implements Subsystem {
    private final WPI_TalonSRX beltFront = new WPI_TalonSRX(22);
    private final WPI_TalonSRX beltBack = new WPI_TalonSRX(23);

    public VerticalStorage() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
    }

    public void setSpeed(double speed) {
        beltFront.set(speed);
        beltBack.set(speed);
    }
}

