package org.frc1410.chargedup2023.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.auto.outside.util.OutsidePreload;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

public class OutsideScoreCollect extends SequentialCommandGroup {
	public OutsideScoreCollect(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new OutsidePreload(drivetrain, lbork, elevator, intake),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, Elevator.State.DRIVING, false),
						Trajectories.OutsideGridToGamePiece(drivetrain)
				),
				new TurnToSmallAngle(drivetrain, 180),
				new MoveElevator(lbork, elevator, intake, Elevator.State.DOWN, false),
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, false),
						new RunIntake(intake),
						Trajectories.OutsideGamePieceToIntake(drivetrain)
				),
				new MoveElevator(lbork, elevator, intake, Elevator.State.DRIVING, false)
		);
	}
}