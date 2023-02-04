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
	private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
	private final NetworkTable table = instance.getTable("Elevator");
	private final DoublePublisher encoderPub = NetworkTables.PublisherFactory(table, "Encoder Value", 0);
	private final BooleanPublisher downMagPub = NetworkTables.PublisherFactory(table, "Down Mag Sensor", false);
	private final BooleanPublisher drivingMagPub = NetworkTables.PublisherFactory(table, "Driving Mag Sensor", false);
	private final BooleanPublisher cubeMagPub = NetworkTables.PublisherFactory(table, "Cube Mag Sensor", false);
	private final BooleanPublisher midMagPub = NetworkTables.PublisherFactory(table, "Mid Mag Sensor", false);

	private final CANSparkMax leaderMotor = new CANSparkMax(ELEVATOR_MOTOR_ONE_ID, MotorType.kBrushless);
	private final CANSparkMax followerMotor = new CANSparkMax(ELEVATOR_MOTOR_TWO_ID, MotorType.kBrushless);
	private final DigitalInput downMagSensor = new DigitalInput(0);
	private final DigitalInput drivingMagSensor = new DigitalInput(1);
	private final DigitalInput cubeMagSensor = new DigitalInput(2);
	private final DigitalInput midMagSensor = new DigitalInput(3);
	private final DigitalInput raisedMagSensor = new DigitalInput(4);

	public enum State {
		DOWN(ELEVATOR_DOWN_POSITION),
		DRIVING(ELEVATOR_DRIVING_POSITION),
		CUBE(ELEVATOR_CUBE_POSITION),
		MID(ELEVATOR_MID_POSITION),
		RAISED(ELEVATOR_RAISED_POSITION);

		private final double position;
		State(double position) {
			this.position = position;
		}

		public double getPosition() {
			return position;
		}
	}


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
	}

	public double getEncoderValue() {
		double leaderPos = leaderMotor.getEncoder().getPosition();
		double followerPos = followerMotor.getEncoder().getPosition();

		return (leaderPos + followerPos) / 2;
	}

	public boolean getDownMagSensorValue() {
		return downMagSensor.get();
	}

	private void setEncoderValue(double value) {
		leaderMotor.getEncoder().setPosition(value);
		followerMotor.getEncoder().setPosition(value);
	}

	@Override
	public void periodic() {
		// Mag sensors return inverted values
		encoderPub.set(getEncoderValue());
		downMagPub.set(!downMagSensor.get());
		drivingMagPub.set(!drivingMagSensor.get());
		cubeMagPub.set(!cubeMagSensor.get());
		midMagPub.set(!midMagSensor.get());

		if (!downMagSensor.get()) {
			setEncoderValue(ELEVATOR_DOWN_POSITION);
		} else if (!drivingMagSensor.get()) {
			setEncoderValue(ELEVATOR_DRIVING_POSITION);
		} else if (!cubeMagSensor.get()) {
			setEncoderValue(ELEVATOR_CUBE_POSITION);
		} else if (!midMagSensor.get()) {
			setEncoderValue(ELEVATOR_MID_POSITION);
		} else if (!raisedMagSensor.get()) {
			setEncoderValue(ELEVATOR_RAISED_POSITION);
		}
	}
}

