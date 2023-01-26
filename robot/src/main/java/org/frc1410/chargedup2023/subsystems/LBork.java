package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.chargedup2023.util.IDs.*;

public class LBork implements Subsystem {

    private final CANSparkMax outerMotor = new CANSparkMax(LBORK_OUTER_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax innerMotor = new CANSparkMax(LBORK_INNER_ROLLER_ID, MotorType.kBrushless);
    public LBork() {
        outerMotor.restoreFactoryDefaults();
        innerMotor.restoreFactoryDefaults();
    }

    public void setRollerSpeeds(double innerRollerSpeed, double outerRollerSpeed) {
        innerMotor.set(innerRollerSpeed);
        outerMotor.set(outerRollerSpeed);
    }

}

