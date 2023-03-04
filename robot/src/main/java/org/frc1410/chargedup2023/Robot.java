package org.frc1410.chargedup2023;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.CaptureScoringPosition;
import org.frc1410.chargedup2023.commands.actions.ResetDrivetrain;
import org.frc1410.chargedup2023.commands.actions.drivetrain.Engage;
import org.frc1410.chargedup2023.commands.actions.elevator.HomeElevator;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorManual;
import org.frc1410.chargedup2023.commands.actions.intake.*;
import org.frc1410.chargedup2023.commands.actions.lbork.*;
import org.frc1410.chargedup2023.commands.groups.teleop.*;
import org.frc1410.chargedup2023.commands.looped.DriveLooped;
import org.frc1410.chargedup2023.commands.looped.HoldElevator;
import org.frc1410.chargedup2023.commands.looped.RunIntakeLooped;
import org.frc1410.chargedup2023.commands.looped.UpdatePoseEstimation;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.chargedup2023.util.generation.TeleopCommandGenerator;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.DeferredTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

	//<editor-fold desc="Controllers">
	private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER, 0.12);
	private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER, 0.25);
	//</editor-fold>

	//<editor-fold desc="Subsystems">
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
	private final ExternalCamera camera = subsystems.track(new ExternalCamera());
	private final Elevator elevator = subsystems.track(new Elevator());
	private final Intake intake = new Intake();
	private final LBork lBork = new LBork();
	private final LightBar lightBar = new LightBar();
	//</editor-fold>

	//<editor-fold desc="Auto Selector">
	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = nt.getTable("Auto");

	private final AutoSelector autoSelector = new AutoSelector()
			.add("Default", SequentialCommandGroup::new)
			.add("Creepy", () -> new Engage(drivetrain));


	private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
			autoSelector.getProfiles().isEmpty() ? "" : autoSelector.getProfiles().get(0).name());

	private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());
	//</editor-fold>

	@Override
	public void autonomousSequence() {
		drivetrain.zeroHeading();
		drivetrain.brakeMode();
		lightBar.set(LightBar.Profile.AUTO);

		NetworkTables.SetPersistence(autoPublisher.getTopic(), true);
		String autoProfile = autoSubscriber.get();
		var autoCommand = autoSelector.select(autoProfile);
		scheduler.scheduleAutoCommand(autoCommand);
	}

	@Override
	public void teleopSequence() {
		drivetrain.brakeMode();
		scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera, lightBar), TaskPersistence.EPHEMERAL);
		drivetrain.zeroHeading();
		lightBar.set(LightBar.Profile.IDLE_STATE);

		//<editor-fold desc="Default Commands">
		scheduler.scheduleDefaultCommand(
				new DriveLooped(
						drivetrain,
						driverController.LEFT_Y_AXIS,
						driverController.RIGHT_Y_AXIS,
						driverController.RIGHT_TRIGGER,
						driverController.LEFT_TRIGGER),
				TaskPersistence.GAMEPLAY
		);

		scheduler.scheduleDefaultCommand(
				new RunIntakeLooped(
						intake,
						lBork,
						operatorController.LEFT_TRIGGER,
						operatorController.RIGHT_TRIGGER),
				TaskPersistence.GAMEPLAY
		);

//		scheduler.scheduleDefaultCommand(
//				new HoldElevator(
//						elevator
//				),
//				TaskPersistence.GAMEPLAY
//		);
		//</editor-fold>

		//<editor-fold desc="Teleop Automation">
		// Possible structure with generator functions
		driverController.RIGHT_BUMPER.whileHeldOnce(DeferredTask.fromCommand(scheduler, () ->
				TeleopCommandGenerator.generateCommand(
						camera,
						drivetrain,
						elevator,
						intake,
						lBork,
						true
				)),
				TaskPersistence.EPHEMERAL
		);

		driverController.LEFT_BUMPER.whileHeldOnce(DeferredTask.fromCommand(scheduler, () ->
					TeleopCommandGenerator.generateCommand(
						camera,
						drivetrain,
						elevator,
						intake,
						lBork,
						false
				)),
				TaskPersistence.EPHEMERAL
		);
		//</editor-fold>

		//<editor-fold desc="Panic Intake Retract">
		driverController.A.whenPressed(
				new PanicMode(intake, elevator, lBork),
				TaskPersistence.EPHEMERAL
		);

		operatorController.A.whenPressed(
				new PanicMode(intake, elevator, lBork),
				TaskPersistence.EPHEMERAL
		);
		//</editor-fold>

		//<editor-fold desc="Operator WhenPressed Commands">
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
						elevator,
						true
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.X.whenPressed(
				new PapaIntakePosition(
						intake,
						elevator,
						lBork,
						lightBar
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.Y.whenPressed(
				new IdleState(
						intake,
						elevator,
						lBork,
						lightBar
				),
				TaskPersistence.EPHEMERAL
		);

		operatorController.START.whenPressed(
				new HomeElevator(intake, lBork, elevator),
				TaskPersistence.EPHEMERAL
		);
		//</editor-fold>

		operatorController.BACK.whenPressed(
				new ResetDrivetrain(drivetrain, camera, true),
				TaskPersistence.EPHEMERAL
		);
	}

	@Override
	public void testSequence() {
		lightBar.set(LightBar.Profile.TEST);
		drivetrain.coastMode();
		// Basic functionality and inversions: Drivetrain
//		scheduler.scheduleDefaultCommand(new DriveLooped(
//						drivetrain,
//						driverController.LEFT_Y_AXIS,
//						driverController.RIGHT_Y_AXIS,
//						driverController.RIGHT_TRIGGER,
//						driverController.LEFT_TRIGGER),
//				TaskPersistence.EPHEMERAL
//		);

		// Basic functionality and inversions: Elevator
		scheduler.scheduleDefaultCommand(new MoveElevatorManual(elevator, driverController.LEFT_Y_AXIS), TaskPersistence.EPHEMERAL);

		// Basic functionality and inversions: LBork
		// Papa Intake
		driverController.A.whileHeld(new RunLBorkPapa(lBork, false), TaskPersistence.EPHEMERAL);
		// Papa outtake
		driverController.B.whileHeld(new RunLBorkPapa(lBork, true), TaskPersistence.EPHEMERAL);
		// Yankee intake
		driverController.X.whileHeld(new RunLBorkYankee(lBork, false), TaskPersistence.EPHEMERAL);
		// Yankee outtake
		driverController.Y.whileHeld(new RunLBorkYankee(lBork, true), TaskPersistence.EPHEMERAL);
//
//		// Superstructure movement and sequencing
		driverController.START.whenPressed(new HomeElevator(intake, lBork, elevator), TaskPersistence.EPHEMERAL);
		driverController.BACK.whenPressed(new ResetDrivetrain(drivetrain, camera, true), TaskPersistence.EPHEMERAL);

		operatorController.A.whenPressed(new ExtendIntake(intake), TaskPersistence.EPHEMERAL);
		operatorController.B.whenPressed(new RetractIntake(intake), TaskPersistence.EPHEMERAL);
		operatorController.X.whileHeld(new ExtendLBork(lBork), TaskPersistence.EPHEMERAL);
		operatorController.Y.whileHeld(new RetractLBork(lBork), TaskPersistence.EPHEMERAL);
	}
}
