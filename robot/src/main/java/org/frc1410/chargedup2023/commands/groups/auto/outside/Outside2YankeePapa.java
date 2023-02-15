package org.frc1410.chargedup2023.commands.groups.auto.outside;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

public class Outside2YankeePapa extends SequentialCommandGroup {
	public Outside2YankeePapa(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new Outside2YankeeCollectPapa(drivetrain, lbork, elevator, intake),
				new TurnToSmallAngle(drivetrain, 48),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, Elevator.State.MID, false),
						Trajectories.OutsideMiddleGamePieceToScorePapa(drivetrain)
				),
				new RetractLBork(lbork)
		);
	}
}