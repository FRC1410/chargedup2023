package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_PAPA_POSITION;

public class PapaIntakePosition extends SequentialCommandGroup {
	public PapaIntakePosition(Intake intake, LBork lBork, Elevator elevator, LightBar lightBar) {
		addRequirements(intake, lBork, elevator);
		addCommands(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.PAPA_PICKUP)),
				new MoveElevator(lBork, elevator, intake, ELEVATOR_PAPA_POSITION, false)
		);
	}
}