package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.chargedup2023.util.IDs.*;

public class LBork implements Subsystem {

    // lebork rollers
    private final CANSparkMax outerMotor = new CANSparkMax(LBORK_OUTER_ROLLER_ID, MotorType.kBrushless);
    private final CANSparkMax innerMotor = new CANSparkMax(LBORK_INNER_ROLLER_ID, MotorType.kBrushless);

    //Lebork actuation
    private final DoubleSolenoid lBorkpiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, LEBORK_PISTON_FOWARD_ID, LEBORK_PISTON_BACKWARDS_ID);

    public LBork() {
        outerMotor.restoreFactoryDefaults();
        innerMotor.restoreFactoryDefaults();
    }

    public void setRollerSpeeds(double innerRollerSpeed, double outerRollerSpeed) {
        innerMotor.set(innerRollerSpeed);
        outerMotor.set(outerRollerSpeed);
    }

    public void extend() {
        lBorkpiston.set(DoubleSolenoid.Value.kForward);
    }

    public void retract(){
        lBorkpiston.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle() {
        lBorkpiston.toggle();
    }

}

