package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_DOWN_POSITION;
import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_DRIVING_POSITION;

public class PanicMode extends SequentialCommandGroup {
	public PanicMode(Intake intake, Elevator elevator, LBork lBork) {
		addRequirements(intake, elevator);
		addCommands(
				new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_DRIVING_POSITION, false),
				new RetractIntake(intake)
		);
	}
}