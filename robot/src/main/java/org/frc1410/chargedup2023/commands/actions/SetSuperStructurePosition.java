package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.framework.util.log.Logger;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;


public class SetSuperStructurePosition extends CommandBase {
	private final Elevator elevator;
	private final Intake intake;
	private final LBork lBork;

	private final double elevatorTargetPosition;
	private final double elevatorInitialPosition;
	private final boolean extendIntake;

	private static final Logger log = new Logger("SetSuperStructurePosition");

	private final Timer timer = new Timer();

	private PIDController pid;

	public SetSuperStructurePosition(Elevator elevator, Intake intake, LBork lBork, double elevatorPosition, boolean extendIntake) {
		this.elevator = elevator;
		this.intake = intake;
		this.lBork = lBork;

		this.elevatorTargetPosition = elevatorPosition;
		this.elevatorInitialPosition = elevator.getPosition();

		this.extendIntake = extendIntake;

		addRequirements();
	}

	private boolean willInterfere() {
		if (
				elevatorInitialPosition > ELEVATOR_INTAKE_INTERFERENCE_HEIGHT
				&& elevatorTargetPosition < ELEVATOR_INTAKE_INTERFERENCE_HEIGHT
		) {
			log.debug("Interference condition encountered");
			return true;
		}

		if (
				elevatorInitialPosition < ELEVATOR_PAPA_POSITION
				&& elevatorTargetPosition >= ELEVATOR_PAPA_POSITION
		) {
			log.debug("Interference condition encountered");
			return true;
		}

		return false;
	}

	@Override
	public void initialize() {
		log.debug("Current Elevator Position: " + elevatorInitialPosition);
		log.debug("Target Elevator position: " + elevatorTargetPosition);

		// Setup PID controller
		pid = new PIDController(ELEVATOR_KP, ELEVATOR_KI, ELEVATOR_KD);
		pid.setSetpoint(elevatorTargetPosition);
		pid.setTolerance(ELEVATOR_TOLERANCE);

		// Decide if we need to extend the intake
		if (willInterfere()) {
			intake.extend();

			if (elevatorTargetPosition > elevatorInitialPosition) {
				intake.setSpeed(-0.5);
			} else if (elevatorTargetPosition < elevatorInitialPosition) {
				intake.setSpeed(0.5);
			}
		}

		timer.restart();
	}

	@Override
	public void execute() {
		if (elevator.getPosition() > ELEVATOR_UPPER_CONFLICT_POSITION) lBork.extend();
		else lBork.retract();

		if (willInterfere() && timer.get() < INTAKE_LBORK_EXTEND_TIME) return;

		// Use PID to set speed
		elevator.setVolts(-pid.calculate(elevator.getPosition()));
	}

	@Override
	public boolean isFinished() {
		// If PID within tolerance
		return pid.atSetpoint();
	}

	@Override
	public void end(boolean interrupted) {
		// Set speed to zero
		elevator.setVolts(0);

		intake.setSpeed(0);

		if (extendIntake) {
			intake.extend();
		} else {
			intake.retract();
		}
	}
}
