package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import org.frc1410.chargedup2023.commands.actions.CaptureScoringPosition;
import org.frc1410.chargedup2023.commands.actions.LookForAprilTag;
import org.frc1410.chargedup2023.commands.actions.ResetDrivetrain;
import org.frc1410.chargedup2023.commands.actions.elevator.HomeElevator;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorManual;
import org.frc1410.chargedup2023.commands.actions.intake.RetractIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.commands.actions.intake.ToggleIntake;
import org.frc1410.chargedup2023.commands.groups.teleop.YankeeIntakePosition;
import org.frc1410.chargedup2023.commands.groups.teleop.PapaIntakePosition;
import org.frc1410.chargedup2023.commands.groups.teleop.DropHeldPiece;
import org.frc1410.chargedup2023.commands.groups.teleop.IdleState;
import org.frc1410.chargedup2023.commands.looped.DriveLooped;
import org.frc1410.chargedup2023.commands.looped.RunIntakeLooped;
import org.frc1410.chargedup2023.commands.looped.UpdatePoseEstimation;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.DRIVER_CONTROLLER;
import static org.frc1410.chargedup2023.util.Constants.OPERATOR_CONTROLLER;

public final class Robot extends PhaseDrivenRobot {

	private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
	private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

	private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
	private final ExternalCamera camera = subsystems.track(new ExternalCamera());
	private final Elevator elevator = subsystems.track(new Elevator());
	private final Intake intake = new Intake();
	private final LBork lBork = new LBork();

	private final LightBar lightBar = new LightBar();

	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = nt.getTable("Auto");

	private final AutoSelector autoSelector = new AutoSelector();

	private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
			autoSelector.getProfiles().isEmpty() ? "" : autoSelector.getProfiles().get(0).name());
	private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

	@Override
	public void autonomousSequence() {
		drivetrain.zeroHeading();
		drivetrain.brakeMode();

		NetworkTables.SetPersistence(autoPublisher.getTopic(), true);
		String autoProfile = autoSubscriber.get();
		var autoCommand = autoSelector.select(autoProfile);
		scheduler.scheduleDefaultCommand(autoCommand, TaskPersistence.EPHEMERAL);
	}

	@Override
	public void teleopSequence() {
		drivetrain.brakeMode();
		scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera), TaskPersistence.EPHEMERAL);

		scheduler.scheduleDefaultCommand(
				new DriveLooped(
						drivetrain,
						driverController.LEFT_Y_AXIS,
						driverController.RIGHT_Y_AXIS,
						driverController.LEFT_TRIGGER,
						driverController.RIGHT_TRIGGER),
				TaskPersistence.GAMEPLAY
		);

		scheduler.scheduleDefaultCommand(
				new RunIntakeLooped(
						intake,
						operatorController.LEFT_TRIGGER,
						operatorController.RIGHT_TRIGGER),
				TaskPersistence.GAMEPLAY
		);

		driverController.LEFT_BUMPER.whenPressed(
				new LookForAprilTag(
						driverController.LEFT_BUMPER,
						drivetrain,
						camera,
						lBork,
						elevator,
						intake,
						scheduler,
						false
				),
				TaskPersistence.EPHEMERAL
		);

		driverController.RIGHT_BUMPER.whenPressed(
				new LookForAprilTag(
						driverController.RIGHT_BUMPER,
						drivetrain,
						camera,
						lBork,
						elevator,
						intake,
						scheduler,
						true
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.RIGHT_BUMPER.whenPressed(
				new CaptureScoringPosition(
						operatorController.LEFT_Y_AXIS,
						operatorController.RIGHT_X_AXIS),
				TaskPersistence.EPHEMERAL
		);

		operatorController.LEFT_BUMPER.whenPressed(
				new DropHeldPiece(
						intake,
						lBork,
						elevator
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.X.whenPressed(
				new PapaIntakePosition(
						intake,
						lBork,
						elevator,
						lightBar
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.B.whenPressed(
				new YankeeIntakePosition(
						intake,
						lBork,
						elevator,
						lightBar
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.Y.whenPressed(
				new IdleState(
						intake,
						lBork,
						elevator,
						lightBar
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.A.whenPressed(
				new RetractIntake(intake),
				TaskPersistence.EPHEMERAL
		);

		operatorController.BACK.whenPressed(
				new ResetDrivetrain(drivetrain, camera),
				TaskPersistence.EPHEMERAL
		);
	}

	@Override
	public void testSequence() {
		drivetrain.coastMode();

		scheduler.scheduleDefaultCommand(new MoveElevatorManual(elevator, operatorController.LEFT_Y_AXIS), TaskPersistence.EPHEMERAL);

		operatorController.LEFT_BUMPER.whenPressed(new ToggleIntake(intake), TaskPersistence.EPHEMERAL);

		operatorController.RIGHT_BUMPER.whileHeld(new HomeElevator(intake, lBork, elevator), TaskPersistence.EPHEMERAL);

		operatorController.Y.whileHeld(new RunLBorkYankee(lBork, false), TaskPersistence.EPHEMERAL);
		operatorController.X.whileHeld(new RunLBorkYankee(lBork, true), TaskPersistence.EPHEMERAL);
		operatorController.B.whileHeld(new RunLBorkPapa(lBork, false), TaskPersistence.EPHEMERAL);
		operatorController.A.whileHeld(new RunLBorkPapa(lBork, true), TaskPersistence.EPHEMERAL);
	}
}
