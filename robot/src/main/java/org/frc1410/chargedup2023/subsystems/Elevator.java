package org.frc1410.chargedup2023.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import org.frc1410.chargedup2023.util.Constants;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

import static org.frc1410.chargedup2023.util.IDs.ELEVATOR_MOTOR_ONE_ID;
import static org.frc1410.chargedup2023.util.IDs.ELEVATOR_MOTOR_TWO_ID;


public class Elevator implements TickedSubsystem {
	private final CANSparkMax leaderMotor = new CANSparkMax(ELEVATOR_MOTOR_ONE_ID, MotorType.kBrushed);
	private final CANSparkMax followerMotor = new CANSparkMax(ELEVATOR_MOTOR_TWO_ID, MotorType.kBrushed);

	private State desiredState = State.DOWN;

	public Elevator() {
		leaderMotor.restoreFactoryDefaults();
		followerMotor.restoreFactoryDefaults();
		followerMotor.follow(leaderMotor);
		followerMotor.setInverted(true);

		leaderMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		followerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void setDesiredState(State target) {
		desiredState = target;
	}

	/**
	 * Gets the state of the elevator if it is known.
	 *
	 * @return The elevator's state if it is at a magnetic read
	 *         switch, or {@code null}.
	 */
	public State getKnownState() {
		for (var possibleState : State.values()) {
			if (possibleState.magSensor.get()) {
				return possibleState;
			}
		}

		return null;
	}

	@Override
	public void periodic() {
		if (desiredState == null) return;

		var knownState = getKnownState();

		if (desiredState == knownState) { // Stop if we hit the state we want.
			leaderMotor.set(0);
			desiredState = null;
		} else if (knownState != null) {
			var diff = knownState.compareTo(desiredState); // Positive if knownState is above state
			var speedMultiplier = diff > 0 ? -1 : 1;

			leaderMotor.set(Constants.ELEVATOR_SPEED * speedMultiplier);
		}
	}

	public enum State {
		DOWN(0),
		STAGE_1(1),
		STAGE_2(2),
		RAISED(3);

		private final DigitalInput magSensor;

		State(int magPort) {
			this.magSensor = new DigitalInput(magPort);
		}
	}
}

