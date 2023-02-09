package org.frc1410.chargedup2023.commands.groups.auto.outside;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkCube;
import org.frc1410.chargedup2023.commands.groups.teleop.MoveElevator;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

public class Outside2ConeCollectCube extends SequentialCommandGroup {
	public Outside2ConeCollectCube(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new Outside2Cone(drivetrain, lbork, elevator, intake),
				new ParallelCommandGroup(
						new MoveElevator(lbork, elevator, intake, Elevator.State.DRIVING, false),
						Trajectories.OutsideScoreToMiddleGamePiece(drivetrain)
				),
				new TurnToSmallAngle(drivetrain, 48-180),
				new ParallelRaceGroup(
						new RunLBorkCube(lbork, false),
						new RunIntake(intake),
						Trajectories.OutsideMiddleGamePieceToIntake(drivetrain)
				)
		);
	}
}