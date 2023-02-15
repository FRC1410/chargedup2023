package org.frc1410.chargedup2023.commands.actions.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.framework.control.Axis;


public class MoveElevatorManual extends CommandBase {
	private final Elevator elevator;
	private final Axis leftYAxis;

	public MoveElevatorManual(Elevator elevator, Axis leftYAxis) {
		this.elevator = elevator;
		this.leftYAxis = leftYAxis;

		addRequirements(elevator);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		elevator.setSpeed(leftYAxis.get());
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void end(boolean interrupted) {
		elevator.setSpeed(0);
	}
}
