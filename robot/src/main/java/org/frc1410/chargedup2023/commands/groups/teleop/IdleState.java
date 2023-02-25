package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.*;

public class IdleState extends SequentialCommandGroup {
	public

	IdleState(Intake intake, LBork lBork, Elevator elevator, LightBar lightBar) {
		super(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_STATE)),
				new ExtendIntake(intake),
				new RetractLBork(lBork),
				new MoveElevatorToPose(elevator, ELEVATOR_IDLE_POSITION),
				new RetractIntake(intake)
		);
	}
}