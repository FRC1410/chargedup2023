package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.chargedup2023.util.IDs.*;

public class Elevator implements Subsystem {
    private final CANSparkMax leaderMotor = new CANSparkMax(ELEVATOR_MOTOR_ONE_ID, MotorType.kBrushed);
    private final CANSparkMax followerMotor = new CANSparkMax(ELEVATOR_MOTOR_TWO_ID, MotorType.kBrushed);

    private final DigitalInput magSensor = new DigitalInput(0);
    
    public Elevator() {
        leaderMotor.restoreFactoryDefaults();
        followerMotor.restoreFactoryDefaults();
        followerMotor.follow(leaderMotor);
    }

    public void setSpeed(double speed) {
        leaderMotor.set(speed);
    }
}

