package org.frc1410.chargedup2023.commands.groups.auto.blue;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.auto.blue.util.BlueBarrierPreload;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BLUE_BARRIER_GRID;
import static org.frc1410.chargedup2023.util.Constants.*;


public class BlueBarrierYankeePapa extends SequentialCommandGroup {
	public BlueBarrierYankeePapa(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, false),
						new WaitCommand(0.3)
				),
				new BlueBarrierPreload(drivetrain, lbork, elevator, intake, true),
				new ParallelCommandGroup(
						new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_PAPA_POSITION, true, false),
						Trajectories.BlueBarrierGridToOklahoma(drivetrain)
				),
				new ParallelCommandGroup(
						Trajectories.BlueOklahomaToScorePapa(drivetrain),
						new SequentialCommandGroup(
								new ParallelRaceGroup(
										new RunLBorkPapa(lbork, false),
										new RunIntake(intake, false),
										new WaitCommand(OKLAHOMA_WAIT)
								),
								new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_RAISED_POSITION, false, true)
						)
				),
				new ParallelRaceGroup(
						new RunLBorkPapa(lbork, true),
						new WaitCommand(PAPA_OUTTAKE_TIME)
				),
				new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_IDLE_POSITION, false, false)
		);
	}
}