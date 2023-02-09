package org.frc1410.chargedup2023.commands.groups.auto.outside;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

public class OutsideScoreCollectEngage extends SequentialCommandGroup {
	public OutsideScoreCollectEngage(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new OutsideScoreCollect(drivetrain, lbork, elevator, intake),
				Trajectories.OutsideGamePieceToChargingStation(drivetrain),
				new Engage(drivetrain)
		);
	}
}