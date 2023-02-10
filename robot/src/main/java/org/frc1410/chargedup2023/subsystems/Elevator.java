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

	private final CANSparkMax leaderMotor = new CANSparkMax(ELEVATOR_MOTOR_ONE_ID, MotorType.kBrushless);
	private final CANSparkMax followerMotor = new CANSparkMax(ELEVATOR_MOTOR_TWO_ID, MotorType.kBrushless);

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
		return State.DOWN.isSensorTripped();
	}

	private void setEncoderValue(double value) {
		leaderMotor.getEncoder().setPosition(value);
		followerMotor.getEncoder().setPosition(value);
	}

	@Override
	public void periodic() {
		// Mag sensors return inverted values
		encoderPub.set(getEncoderValue());

		for (var state : State.values()) {
			var tripped = state.isSensorTripped();
			state.debugEntry.set(tripped);

			if (tripped) {
				setEncoderValue(state.position);
			}
		}
	}

	public enum State {
		DOWN(ELEVATOR_DOWN_POSITION, 0),
		DRIVING(ELEVATOR_DRIVING_POSITION, 1),
		CUBE(ELEVATOR_CUBE_POSITION, 2),
		MID(ELEVATOR_MID_POSITION, 3),
		RAISED(ELEVATOR_RAISED_POSITION, 4);

		private final double position;
		private final BooleanPublisher debugEntry;
		private final DigitalInput sensor;

		State(double position, int sensorPort) {
			var networkTable = NetworkTableInstance.getDefault().getTable("Elevator");

			this.position = position;
			this.debugEntry = NetworkTables.PublisherFactory(networkTable, "Mag Sensor (" + name() + ")", false);
			this.sensor = new DigitalInput(sensorPort);
		}

		public double getPosition() {
			return position;
		}

		/**
		 * Checks if the mag sensor at this state is tripped.
		 *
		 * @return {@code true} if the sensor is tripped or is not
		 *         properly connected.
		 */
		public boolean isSensorTripped() {
			return !sensor.get(); // output goes low when a magnet is picked up
		}
	}
}
