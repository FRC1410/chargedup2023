package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.*;

public class IdleState extends SequentialCommandGroup {
	public

	IdleState(Intake intake, Elevator elevator, LightBar lightBar) {
		addCommands(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_STATE)),
				new SetSuperStructurePosition(elevator, intake, ELEVATOR_IDLE_POSITION, false)
		);
	}
}