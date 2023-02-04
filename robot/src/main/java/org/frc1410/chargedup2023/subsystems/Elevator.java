package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.ELEVATOR_MOTOR_ONE_ID;
import static org.frc1410.chargedup2023.util.IDs.ELEVATOR_MOTOR_TWO_ID;
import static org.frc1410.chargedup2023.util.Constants.*;

public class Elevator implements TickedSubsystem {
	NetworkTableInstance instance = NetworkTableInstance.getDefault();
	NetworkTable table = instance.getTable("Elevator");
	DoublePublisher encoderPub = NetworkTables.PublisherFactory(table, "Encoder Value", 0);
	BooleanPublisher downMagPub = NetworkTables.PublisherFactory(table, "Down Mag Sensor", false);
	BooleanPublisher drivingMagPub = NetworkTables.PublisherFactory(table, "Driving Mag Sensor", false);
	BooleanPublisher cubeMagPub = NetworkTables.PublisherFactory(table, "Cube Mag Sensor", false);
	BooleanPublisher midMagPub = NetworkTables.PublisherFactory(table, "Mid Mag Sensor", false);
	BooleanPublisher raisedMagPub = NetworkTables.PublisherFactory(table, "Raised Mag Sensor", false);

	public final CANSparkMax leaderMotor = new CANSparkMax(ELEVATOR_MOTOR_ONE_ID, MotorType.kBrushed);
	private final CANSparkMax followerMotor = new CANSparkMax(ELEVATOR_MOTOR_TWO_ID, MotorType.kBrushed);
	private final DigitalInput downMagSensor = new DigitalInput(0);
	private final DigitalInput drivingMagSensor = new DigitalInput(1);
	private final DigitalInput cubeMagSensor = new DigitalInput(2);
	private final DigitalInput midMagSensor = new DigitalInput(3);
	private final DigitalInput raisedMagSensor = new DigitalInput(4);

	private double poseOffset = 0;

	public Elevator() {
		leaderMotor.restoreFactoryDefaults();
		followerMotor.restoreFactoryDefaults();
		followerMotor.follow(leaderMotor);
		followerMotor.setInverted(true);

		leaderMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		followerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void setSpeed(double speed) {
		leaderMotor.set(speed);
		followerMotor.set(speed);
	}

	public double getEncoderValue() {
		double leaderPos = leaderMotor.getEncoder().getPosition();
		double followerPos = followerMotor.getEncoder().getPosition();

		return (leaderPos + followerPos) / 2 + poseOffset;
	}

	private void setEncoderValue(double value) {
		poseOffset = value - getEncoderValue();
	}

	@Override
	public void periodic() {
		// Mag sensors return inverted values
		encoderPub.set(getEncoderValue());
		downMagPub.set(!downMagSensor.get());
		drivingMagPub.set(!drivingMagSensor.get());
		cubeMagPub.set(!cubeMagSensor.get());
		midMagPub.set(!midMagSensor.get());
		raisedMagPub.set(!raisedMagSensor.get());

		if (!downMagSensor.get()) setEncoderValue(ELEVATOR_DOWN_POSITION);
		else if (!drivingMagSensor.get()) setEncoderValue(ELEVATOR_DRIVING_POSITION);
		else if (!cubeMagSensor.get()) setEncoderValue(ELEVATOR_CUBE_POSITION);
		else if (!midMagSensor.get()) setEncoderValue(ELEVATOR_MID_POSITION);
		else if (!raisedMagSensor.get()) setEncoderValue(ELEVATOR_RAISED_POSITION);
	}
}

