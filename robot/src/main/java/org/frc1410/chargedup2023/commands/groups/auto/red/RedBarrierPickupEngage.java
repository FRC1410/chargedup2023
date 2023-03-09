package org.frc1410.chargedup2023.commands.groups.auto.red;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Creepy;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

public class RedBarrierPickupEngage extends SequentialCommandGroup {
	public RedBarrierPickupEngage(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new RedBarrierPickup(drivetrain, lbork, elevator, intake),
				new Creepy(drivetrain, false),
				new Engage(drivetrain)
		);
	}
}
