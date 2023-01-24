package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import static org.frc1410.chargedup2023.util.IDs.*;

public class Intake implements TickedSubsystem {

    private final CANSparkMax intake = new CANSparkMax(INTAKE_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DoubleSolenoid flipper = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, INTAKE_FLIPPER_FOWARD_ID, INTAKE_FLIPPER_BACKWARD_ID);

    public Intake() {

    }

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

    public void setFlipperFoward() {
        flipper.set(DoubleSolenoid.Value.kForward);
    }

    public void setFlipperOff() {
        flipper.set(DoubleSolenoid.Value.kOff);
    }

    public void toggle(){
        flipper.toggle();
    }
    public void setFlipperReversed() {
        flipper.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void periodic() {

    }
}

