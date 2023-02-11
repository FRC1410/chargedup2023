package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static org.frc1410.chargedup2023.util.IDs.*;

public class LBork implements Subsystem {
	
	// L'Bork rollers
	private final CANSparkMax outerMotor = new CANSparkMax(LBORK_OUTER_ROLLER_ID, MotorType.kBrushless);
	private final CANSparkMax innerMotor = new CANSparkMax(LBORK_INNER_ROLLER_ID, MotorType.kBrushless);

	// L'Bork actuation
	private final DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.REVPH, LBORK_PISTON_FORWARD_ID, LBORK_PISTON_BACKWARDS_ID);

	public LBork() {
		innerMotor.restoreFactoryDefaults();
		outerMotor.restoreFactoryDefaults();

		innerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		outerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void setRollerSpeeds(double innerRollerSpeed, double outerRollerSpeed) {
		innerMotor.set(innerRollerSpeed);
		outerMotor.set(outerRollerSpeed);
	}

	public void extend() {
		piston.set(DoubleSolenoid.Value.kForward);
	}

	public void retract() {
		piston.set(DoubleSolenoid.Value.kReverse);
	}

	public void toggle() {
		piston.toggle();
	}
}

