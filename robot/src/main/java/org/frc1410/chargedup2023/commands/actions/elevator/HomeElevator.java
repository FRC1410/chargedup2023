package org.frc1410.chargedup2023.commands.actions.elevator;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.intake.ExtendIntake;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RetractLBork;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;

public class HomeElevator extends SequentialCommandGroup {
	public HomeElevator(Intake intake, LBork lBork, Elevator elevator) {
		super(
				new ExtendIntake(intake),
				new RetractLBork(lBork),
				new ElevatorHomingSequence(elevator),
				new RetractIntake(intake)
		);
	}
}