package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.*;

public class DropHeldPiece extends SequentialCommandGroup {
	public DropHeldPiece(Intake intake, LBork lBork, Elevator elevator, boolean papa) {
		addRequirements(intake, lBork, elevator);
		addCommands(
				new SetSuperStructurePosition(elevator, intake, ELEVATOR_MID_POSITION, false),
				new ParallelRaceGroup(
						papa
								? new RunLBorkPapa(lBork, true)
								: new RunLBorkYankee(lBork, true),
						new WaitCommand(OUTTAKE_TIME)
				),
				new SetSuperStructurePosition(elevator, intake, ELEVATOR_DRIVING_POSITION, false)
		);
	}
}