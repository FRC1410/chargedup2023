package org.frc1410.chargedup2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.*;


public class LBork implements TickedSubsystem {
	private final NetworkTable table = NetworkTableInstance.getDefault().getTable("LBork");
	private final BooleanPublisher linePub = NetworkTables.PublisherFactory(table, "Line Break", false);
	
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

		innerMotor.setSmartCurrentLimit(7);
		outerMotor.setSmartCurrentLimit(7);

		innerMotor.setInverted(true);
	}

	@Override
	public void periodic() {
//		linePub.set(getLineBreak());
	}

	public void setRollerSpeeds(double outerRollerSpeed, double innerRollerSpeed) {
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

//	public boolean getLineBreak() {
//		return innerMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed).isPressed();
//	}
}

