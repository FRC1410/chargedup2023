package org.frc1410.chargedup2023.commands.groups.auto.outside;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.util.Constants.OUTTAKE_TIME;

public class Outside2Yankee extends SequentialCommandGroup {
	public Outside2Yankee(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new OutsideScoreCollect(drivetrain, lbork, elevator, intake),
				new TurnToSmallAngle(drivetrain, 0),
				new ParallelCommandGroup(
						Trajectories.OutsideGamePieceToScoreAngled(drivetrain),
						new MoveElevator(lbork, elevator, intake, Elevator.State.RAISED, true)
				),
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, true),
						new WaitCommand(OUTTAKE_TIME)
				),
				new RetractLBork(lbork)
		);
	}
}