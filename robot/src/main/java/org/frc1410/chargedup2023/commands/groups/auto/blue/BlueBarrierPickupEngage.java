package org.frc1410.chargedup2023.commands.groups.auto.blue;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Creepy;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

public class BlueBarrierPickupEngage extends SequentialCommandGroup {
	public BlueBarrierPickupEngage(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new BlueBarrierPickup(drivetrain, lbork, elevator, intake),
				new Creepy(drivetrain, false, 0.5),
				new Engage(drivetrain)
		);
	}
}
