package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_PAPA_POSITION;
import static org.frc1410.chargedup2023.util.Constants.INTAKE_LBORK_EXTEND_TIME;

public class PapaIntakePosition extends SequentialCommandGroup {
	public PapaIntakePosition(Intake intake, LBork lBork, Elevator elevator, LightBar lightBar) {
		super(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.PAPA_PICKUP)),
				new ParallelCommandGroup(
						new ExtendIntake(intake),
						new SequentialCommandGroup(
								new RetractLBork(lBork),
								new WaitCommand(INTAKE_LBORK_EXTEND_TIME)
						)
				),
				new MoveElevatorToPose(elevator, ELEVATOR_PAPA_POSITION)
		);
	}
}