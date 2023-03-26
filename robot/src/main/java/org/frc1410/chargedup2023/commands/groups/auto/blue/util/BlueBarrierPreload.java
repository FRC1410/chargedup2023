package org.frc1410.chargedup2023.commands.groups.auto.blue.util;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.subsystems.*;

import static org.frc1410.chargedup2023.auto.POIs.BLUE_BARRIER_GRID;
import static org.frc1410.chargedup2023.util.Constants.*;

public class BlueBarrierPreload extends SequentialCommandGroup {
	public BlueBarrierPreload(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake, boolean isComponent) {
		drivetrain.resetPoseEstimation(BLUE_BARRIER_GRID);

		addCommands(
				new ExtendIntake(intake),
				new WaitCommand(0.5),
				new ParallelRaceGroup(
						new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_RAISED_POSITION, true, true),
						new RunLBorkYankee(lbork, false)
				),
				new WaitCommand(0.25),
				new ParallelRaceGroup(
						new RunLBorkYankee(lbork, true),
						new WaitCommand(YANKEE_OUTTAKE_TIME)
				),
				new RetractLBork(lbork),
				isComponent
						? new InstantCommand(() -> {})
						: new SetSuperStructurePosition(elevator, intake, lbork, ELEVATOR_IDLE_POSITION, false, false)

				);
	}
}
