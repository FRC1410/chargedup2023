package org.frc1410.test.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Intake;
import org.frc1410.framework.control.Axis;


public class RunIntake extends CommandBase {

	private final Intake intake;
	private final Axis axis;

	public RunIntake(Intake intake, Axis axis) {
		this.intake = intake;
		this.axis = axis;


		// each subsystem used by the command must be passed into the
		// addRequirements() method (which takes a vararg of Subsystem)
		addRequirements();
	}

	@Override
	public void execute() {
		intake.setIntakeSpeed(axis.get());
	}

	@Override
	public void end(boolean interrupted) {
		intake.setIntakeSpeed(0);
	}
}
