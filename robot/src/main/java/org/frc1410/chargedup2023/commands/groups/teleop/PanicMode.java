package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_DOWN_POSITION;

public class PanicMode extends SequentialCommandGroup {
	public PanicMode(Intake intake, Elevator elevator) {
		addCommands(
				new MoveElevatorToPose(elevator, ELEVATOR_DOWN_POSITION),
				new RetractIntake(intake)
		);
	}
}