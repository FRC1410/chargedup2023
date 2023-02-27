package org.frc1410.chargedup2023.commands.groups.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import static org.frc1410.chargedup2023.util.Constants.CREEPY_WAIT;

public class Creepy extends SequentialCommandGroup {
	public Creepy(Drivetrain drivetrain, boolean reversed) {
		addCommands(
				new InstantCommand(() -> {
					if (reversed)
						drivetrain.autoTankDriveVolts(6, 6);
					else
						drivetrain.autoTankDriveVolts(-6, -6);
				}),
				new WaitCommand(CREEPY_WAIT),
				new InstantCommand(() -> drivetrain.autoTankDriveVolts(0, 0)),
				new Engage(drivetrain)
		);
	}
}
