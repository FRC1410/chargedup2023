package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;
import static org.frc1410.chargedup2023.util.IDs.*;

public class Intake implements Subsystem {

    private final CANSparkMax intakeMotor = new CANSparkMax(INTAKE_MOTOR_ID, MotorType.kBrushless);
    private final DoubleSolenoid intakeFlipper = new DoubleSolenoid(PneumaticsModuleType.REVPH, INTAKE_FLIPPER_FOWARD_ID, INTAKE_FLIPPER_BACKWARD_ID);

    public Intake() {
        intakeMotor.restoreFactoryDefaults();
    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public void extend() {
        intakeFlipper.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        intakeFlipper.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggle(){
        intakeFlipper.toggle();
    }
}

