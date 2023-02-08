package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.ExtendLBork;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

import static org.frc1410.chargedup2023.util.Constants.INTAKE_LBORK_EXTEND_TIME;

public class MoveElevator extends SequentialCommandGroup {
	public MoveElevator(LBork lbork, Elevator elevator, Intake intake, Elevator.State state, boolean extendIntake) {
		addCommands(
				new ExtendIntake(intake),
				new RetractLBork(lbork),
				new WaitCommand(INTAKE_LBORK_EXTEND_TIME),
				new MoveElevatorToPose(elevator, state),
				extendIntake ? new ExtendLBork(lbork) : null
		);
	}
}
