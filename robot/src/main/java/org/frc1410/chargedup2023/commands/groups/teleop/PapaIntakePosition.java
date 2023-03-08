package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_PAPA_POSITION;

public class PapaIntakePosition extends SequentialCommandGroup {
	public PapaIntakePosition(Intake intake, Elevator elevator, LBork lBork, LightBar lightBar) {
		addRequirements(intake, elevator);
		addCommands(
				new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_PAPA_POSITION, true, false)
		);
	}
}