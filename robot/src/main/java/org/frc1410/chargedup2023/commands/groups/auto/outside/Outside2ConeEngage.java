package org.frc1410.chargedup2023.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

public class Outside2ConeEngage extends SequentialCommandGroup {
	public Outside2ConeEngage(Drivetrain drivetrain) {
		addCommands();
	}
}