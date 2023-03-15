package org.frc1410.chargedup2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;
import org.frc1410.framework.util.log.Logger;

import static org.frc1410.chargedup2023.util.IDs.*;


public class LBork implements TickedSubsystem {

	private static final Logger log = new Logger("LBork");
	private final NetworkTable table = NetworkTableInstance.getDefault().getTable("LBork");
	private final BooleanPublisher lineBreakPub = NetworkTables.PublisherFactory(table, "Line Break", false);
	
	// L'Bork rollers
	private final CANSparkMax outerMotor = new CANSparkMax(LBORK_OUTER_ROLLER_ID, MotorType.kBrushless);
	private final CANSparkMax innerMotor = new CANSparkMax(LBORK_INNER_ROLLER_ID, MotorType.kBrushless);
	private final DigitalInput limitSwitch = new DigitalInput(LBORK_LIMIT_SWITCH_PORT);

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
		lineBreakPub.set(limitSwitch.get());
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

	public boolean getLimitSwitch() {
		return limitSwitch.get();
	}
}

