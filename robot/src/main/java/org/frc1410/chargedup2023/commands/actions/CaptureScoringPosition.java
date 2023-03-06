package org.frc1410.chargedup2023.commands.actions;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.control.Axis;

import static org.frc1410.chargedup2023.util.Constants.*;


public class CaptureScoringPosition extends CommandBase {

	private final DoublePublisher selectionIndexPub = NetworkTables.PublisherFactory(
			NetworkTableInstance.getDefault().getTable("Drivetrain"),
			"Scoring Position Index",
			-1
	);

	private final Axis leftY;
	private final Axis rightX;

	public CaptureScoringPosition(Axis leftY, Axis rightX) {
		this.leftY = leftY;
		this.rightX = rightX;
	}

	@Override
	public void initialize() {
		int x = (int) Math.signum(rightX.get());
		int y = (int) -Math.signum(leftY.get());

		ScoringPosition.targetPosition = ScoringPosition.fromCoords(x, y);
		selectionIndexPub.accept(ScoringPosition.targetPosition.ordinal());
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
