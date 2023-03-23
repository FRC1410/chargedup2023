package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import java.util.ArrayList;

import static org.frc1410.chargedup2023.util.Constants.*;

public class PapaIntakePosition extends SequentialCommandGroup {
	public PapaIntakePosition(Intake intake, Elevator elevator, LBork lBork) {
		addRequirements(intake, elevator);

		var commands = new ArrayList<Command>();

		if (elevator.getPosition() == ELEVATOR_IDLE_POSITION) {
			commands.add(new ExtendIntake(intake));
			commands.add(new WaitCommand(0.15));
		}

		commands.add(new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_PAPA_POSITION, true, false));

		addCommands(
				commands.toArray(new Command[0])
		);
	}
}