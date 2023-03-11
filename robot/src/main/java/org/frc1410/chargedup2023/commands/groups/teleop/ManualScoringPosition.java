package org.frc1410.chargedup2023.commands.groups.teleop;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.CaptureScoringPosition;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.Elevator;
import org.frc1410.chargedup2023.subsystems.Intake;
import org.frc1410.chargedup2023.subsystems.LBork;
import org.frc1410.framework.control.Axis;

import static org.frc1410.chargedup2023.util.Constants.*;

public class ManualScoringPosition extends SequentialCommandGroup {
	public ManualScoringPosition(Axis leftY, Axis rightX, Elevator elevator, LBork lBork, Intake intake) {

		int y = (int) -Math.signum(leftY.get());

		var elevatorPose = switch(y) {
			case 1 -> ELEVATOR_RAISED_POSITION;
			case 0 -> ELEVATOR_MID_POSITION;
			case -1 -> ELEVATOR_HYBRID_POSITION;
			default -> ELEVATOR_IDLE_POSITION;
		};

		var extendLBork = elevatorPose == ELEVATOR_RAISED_POSITION;

		addCommands(
				new CaptureScoringPosition(leftY, rightX),
				new SetSuperStructurePosition(elevator, intake, lBork, elevatorPose, true, extendLBork)
		);
	}
}