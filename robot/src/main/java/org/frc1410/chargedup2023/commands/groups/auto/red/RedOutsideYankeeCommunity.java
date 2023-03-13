package org.frc1410.chargedup2023.commands.groups.auto.red;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Creepy;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.auto.blue.util.BlueBarrierPreload;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.CREEPY_TIME;
import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_DRIVING_POSITION;

public class RedOutsideYankeeCommunity extends SequentialCommandGroup {
	public RedOutsideYankeeCommunity(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, false),
						new WaitCommand(0.3)
				),
				new BlueBarrierPreload(drivetrain, lbork, elevator, intake, true),
				new ParallelCommandGroup(
						new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_DRIVING_POSITION, false, false),
						new Creepy(drivetrain, true, CREEPY_TIME + 1.5)
				),
				new Engage(drivetrain)
		);
	}
}