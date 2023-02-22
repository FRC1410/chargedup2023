package org.frc1410.chargedup2023.subsystems;


import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
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
	DoublePublisher encoderPub = NetworkTables.PublisherFactory(table, "Encoder Value", 0);

	private final CANSparkMax leftMotor = new CANSparkMax(ELEVATOR_LEFT_MOTOR_ID, MotorType.kBrushless);
	private final CANSparkMax rightMotor = new CANSparkMax(ELEVATOR_RIGHT_MOTOR_ID, MotorType.kBrushless);

//	private final WPI_TalonSRX encoder = new WPI_TalonSRX(ELEVATOR_ENCODER_ID);

	private final DigitalInput limitSwitch = new DigitalInput(ELEVATOR_LIMIT_SWITCH_PORT);

	public Elevator() {
		leftMotor.restoreFactoryDefaults();
		rightMotor.restoreFactoryDefaults();

		leftMotor.setInverted(true);
		rightMotor.setInverted(false);

		leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

		leftMotor.setSmartCurrentLimit(10, 40);
		rightMotor.setSmartCurrentLimit(10, 40);

//		leftMotor.enableSoftLimit();

//		encoder.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
//		encoder.configFeedbackNotContinuous(false, 10);
	}

	public void setSpeed(double speed) {
		leftMotor.set(speed);
		rightMotor.set(speed);
	}

	public void setVolts(double volts) {
		leftMotor.setVoltage(volts);
		rightMotor.setVoltage(volts);
	}

	public double getEncoderValue() {
//		return encoder.getSelectedSensorPosition() /* ELEVATOR_ENCODER_CONSTANT*/;
		double encoderRevolutions = (leftMotor.getEncoder().getPosition() + rightMotor.getEncoder().getPosition()) / 2;
		return encoderRevolutions * ELEVATOR_NEO_ENCODER_CONSTANT;
		// This method returns inches (elevator relative)
	}

	public boolean getLimitSwitchValue() {
		return limitSwitch.get();
	}

	public void setEncoderValue(double value) {
//		encoder.setSelectedSensorPosition(value / ELEVATOR_ENCODER_CONSTANT);
		// The value input should be in inches so revolutions are set
		leftMotor.getEncoder().setPosition(value / ELEVATOR_NEO_ENCODER_CONSTANT);
		rightMotor.getEncoder().setPosition(value / ELEVATOR_NEO_ENCODER_CONSTANT);
	}

	@Override
	public void periodic() {
		encoderPub.set(getEncoderValue());
		System.out.println("Elevator Encoder: " + getEncoderValue());
	}
}
