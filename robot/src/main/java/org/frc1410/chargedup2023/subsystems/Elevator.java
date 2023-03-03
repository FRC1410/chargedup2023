package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.IDs.*;

public class Elevator implements TickedSubsystem {

	private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
	private final NetworkTable table = instance.getTable("Elevator");
	private final DoublePublisher encoderPub = NetworkTables.PublisherFactory(table, "Encoder Value", 0);
	private final BooleanPublisher limitPub = NetworkTables.PublisherFactory(table, "Limit Switch", false);

	private final CANSparkMax leftMotor = new CANSparkMax(ELEVATOR_LEFT_MOTOR_ID, MotorType.kBrushless);
	private final CANSparkMax rightMotor = new CANSparkMax(ELEVATOR_RIGHT_MOTOR_ID, MotorType.kBrushless);

	private final RelativeEncoder leftEncoder = leftMotor.getEncoder();
	private final RelativeEncoder rightEncoder = rightMotor.getEncoder();

	private final DigitalInput limitSwitch = new DigitalInput(ELEVATOR_LIMIT_SWITCH_PORT);

	public Elevator() {
		leftMotor.restoreFactoryDefaults();
		rightMotor.restoreFactoryDefaults();

		leftMotor.setInverted(true);
		rightMotor.setInverted(false);

		leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

		leftMotor.setSmartCurrentLimit(10, 20);
		rightMotor.setSmartCurrentLimit(10, 20);
	}

	public void setSpeed(double speed) {
		leftMotor.set(speed);
		rightMotor.set(speed);
	}

	public void setVolts(double volts) {
		leftMotor.setVoltage(volts);
		rightMotor.setVoltage(volts);
	}

	public double getPosition() {
		return ((leftEncoder.getPosition() + rightEncoder.getPosition()) / 2) * ELEVATOR_NEO_ENCODER_CONSTANT;
	}

	public boolean getLimitSwitchValue() {
		return limitSwitch.get();
	}

	public void resetPosition(double value) {
		leftEncoder.setPosition(value / ELEVATOR_NEO_ENCODER_CONSTANT);
		rightEncoder.setPosition(value / ELEVATOR_NEO_ENCODER_CONSTANT);
	}

	@Override
	public void periodic() {
		encoderPub.set(getPosition());
		limitPub.set(getLimitSwitchValue());
	}
}
