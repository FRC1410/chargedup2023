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

import static org.frc1410.chargedup2023.util.Constants.*;

public class YankeeIntakePosition extends SequentialCommandGroup {
	public YankeeIntakePosition(Intake intake, LBork lBork, Elevator elevator, LightBar lightBar) {
		addRequirements(intake, lBork, elevator);
		addCommands(
				new InstantCommand(() -> lightBar.set(LightBar.Profile.YANKEE_PICKUP)),
				new MoveElevator(lBork, elevator, intake, ELEVATOR_SUBSTATION_POSITION, false),
				new RetractIntake(intake)
		);
	}
}