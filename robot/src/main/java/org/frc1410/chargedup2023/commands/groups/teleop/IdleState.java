package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.*;

public class IdleState extends SequentialCommandGroup {
	public IdleState(Intake intake, Elevator elevator, LBork lBork, LightBar lightBar) {
		addCommands(
				new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_IDLE_POSITION, false, false)
		);
	}
}