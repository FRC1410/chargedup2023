package org.frc1410.chargedup2023.commands.groups.auto.barrier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Creepy;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.util.BarrierPreload;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_DRIVING_POSITION;

public class BarrierYankeeEngage extends SequentialCommandGroup {
	public BarrierYankeeEngage(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new BarrierPreload(drivetrain, lbork, elevator, intake, true),
				new ParallelCommandGroup(
						new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_DRIVING_POSITION, false, false),
						new Creepy(drivetrain, true)
				),
				new Engage(drivetrain)
		);
	}
}