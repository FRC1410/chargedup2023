package org.frc1410.chargedup2023.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class VerticalStorage implements Subsystem {
    private final WPI_TalonSRX belt_front = new WPI_TalonSRX(0);
    private final WPI_TalonSRX belt_back = new WPI_TalonSRX(0);

    public VerticalStorage() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
    }

    public void setSpeed(double speed) {
        belt_front.set(speed);
        belt_back.set(speed);
    }
}

