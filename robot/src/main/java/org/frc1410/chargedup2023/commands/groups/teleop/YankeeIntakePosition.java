package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorToPose;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.ExtendLBork;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.subsystems.LightBar;

import static org.frc1410.chargedup2023.util.Constants.ELEVATOR_MID_POSITION;
import static org.frc1410.chargedup2023.util.Constants.INTAKE_LBORK_EXTEND_TIME;

public class YankeeIntakePosition extends SequentialCommandGroup {
	public YankeeIntakePosition(Intake intake, LBork lBork, Elevator elevator, LightBar lightBar) {
		addCommands(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.YANKEE_PICKUP)),
				new MoveElevator(lBork, elevator, intake, ELEVATOR_MID_POSITION, false),
				new RetractIntake(intake)
		);
	}
}