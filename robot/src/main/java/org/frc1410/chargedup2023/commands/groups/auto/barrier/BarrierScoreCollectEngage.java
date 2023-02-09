package org.frc1410.chargedup2023.commands.groups.auto.barrier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.groups.auto.Creepy;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.chargedup2023.util.Trajectories;

public class BarrierScoreCollectEngage extends SequentialCommandGroup {
	public BarrierScoreCollectEngage(Drivetrain drivetrain, LBork lbork, Elevator elevator, Intake intake) {
		addCommands(
				new BarrierScoreCollect(drivetrain, lbork, elevator, intake),
				Trajectories.BarrierGamePieceToChargingStation(drivetrain),
				new Creepy(drivetrain, false)
		);
	}
}