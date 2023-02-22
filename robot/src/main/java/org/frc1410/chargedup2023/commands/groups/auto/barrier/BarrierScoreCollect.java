package org.frc1410.chargedup2023.commands.groups.auto.barrier;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.util.BarrierPreload;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.util.Constants.*;

public class BarrierScoreCollect extends SequentialCommandGroup {
	public BarrierScoreCollect(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new BarrierPreload(drivetrain, lbork, elevator, intake),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, ELEVATOR_DRIVING_POSITION, false),
						Trajectories.BarrierGridToGamePiece(drivetrain)
				),
				new TurnToSmallAngle(drivetrain, 180),
				new MoveElevator(lbork, elevator, intake, ELEVATOR_DOWN_POSITION, false),
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, false),
						new RunIntake(intake),
						Trajectories.BarrierGamePieceToIntake(drivetrain)
				),
				new MoveElevator(lbork, elevator, intake, ELEVATOR_DRIVING_POSITION, false)
		);
	}
}