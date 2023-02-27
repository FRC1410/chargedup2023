package org.frc1410.chargedup2023.commands.groups.auto.barrier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.util.Constants.*;

public class Barrier2YankeeCollectPapa extends SequentialCommandGroup {
	public Barrier2YankeeCollectPapa(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new Barrier2Yankee(drivetrain, lbork, elevator, intake),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, ELEVATOR_MID_POSITION, false),
						Trajectories.BarrierScoreToMiddleGamePiece(drivetrain)
				),
				new TurnToSmallAngle(drivetrain, -48-180),
				new ParallelRaceGroup(
						new RunLBorkPapa(lbork, false),
						new RunIntake(intake),
						Trajectories.BarrierMiddleGamePieceToIntake(drivetrain)
				)
		);
	}
}