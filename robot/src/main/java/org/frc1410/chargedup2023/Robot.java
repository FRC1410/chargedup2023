package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.commands.actions.CaptureScoringPosition;
import org.frc1410.chargedup2023.commands.actions.ResetDrivetrain;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.commands.actions.elevator.HomeElevator;
import org.frc1410.chargedup2023.commands.actions.elevator.MoveElevatorManual;
import org.frc1410.chargedup2023.commands.actions.intake.*;
import org.frc1410.chargedup2023.commands.actions.lbork.*;
import org.frc1410.chargedup2023.commands.groups.auto.blue.*;
import org.frc1410.chargedup2023.commands.groups.auto.red.*;
import org.frc1410.chargedup2023.commands.groups.teleop.*;
import org.frc1410.chargedup2023.commands.looped.*;
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
	private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER, 0.2);
	private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER, 0.25);
	//</editor-fold>

	//<editor-fold desc="Subsystems">
	private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
	private final ExternalCamera camera = subsystems.track(new ExternalCamera());
	private final Elevator elevator = subsystems.track(new Elevator());
	private final Intake intake = new Intake();
	private final LBork lBork = subsystems.track(new LBork());
	private final LightBar lightBar = new LightBar();
	//</editor-fold>

	//<editor-fold desc="Auto Selector">
	private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
	private final NetworkTable table = nt.getTable("Auto");

	{
		var layout = """
		[{
			"tabName": "Drive",
			"id": "drive",

			"components": [{
				"type": "string_select",
				"title": "Auto Selection",
				"layout": {
					"pos": [1, 1],
					"size": [2, 1]
				},
				"topics": ["Auto/Choices", "Auto/Selection"]
			}, {
				"type": "clock",
				"title": "Game Time",
				"layout": {
					"pos": [3, 1],
					"size": [2, 1]
				},
				"topics": ["FMSInfo/GameTime"]
			}, {
				"type": "node_select",
				"title": "Selected Node",
				"layout": {
					"pos": [5, 1],
					"size": [1, 1]
				},
				"topics": ["Drivetrain/Scoring Pose Index"]
			}, {
				"type": "boolean",
				"title": "L'Bork Line Break",
				"layout": {
					"pos": [6, 1],
					"size": [1, 1]
				},
				"topics": ["LBork/Line Break"]
			}]
		}]""";
		// grid, line break, auto, time
		var pub = NetworkTables.PublisherFactory(nt.getTable("viridian"), "layout", layout);
	}

	private final AutoSelector autoSelector = new AutoSelector()
//			.add("Default", SequentialCommandGroup::new)
			.add("B2P", () -> new BlueBarrierYankeePapa(drivetrain, lBork, elevator, intake))
			.add("BSEND", () -> new BlueBarrierYankeeEngage(drivetrain, lBork, elevator, intake))
			.add("BSENDL", () -> new BlueOutsideYankeeCommunity(drivetrain, lBork, elevator, intake))
			.add("B1PPE", () -> new BlueBarrierPickupEngage(drivetrain, lBork, elevator, intake))
			.add("R2P", () -> new RedBarrierYankeePapa(drivetrain, lBork, elevator, intake))
			.add("RSEND", () -> new RedBarrierYankeeEngage(drivetrain, lBork, elevator, intake))
			.add("RSENDL", () -> new RedOutsideYankeeCommunity(drivetrain, lBork, elevator, intake))
			.add("R1PPE", () -> new RedBarrierPickupEngage(drivetrain, lBork, elevator, intake));
	{
		var profiles = new String[autoSelector.getProfiles().size()];
		for (var i = 0; i < profiles.length; i++) {
			profiles[i] = autoSelector.getProfiles().get(i).name();
		}

		var pub = NetworkTables.PublisherFactory(table, "Choices", profiles);
		pub.accept(profiles);
	}

	private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
			autoSelector.getProfiles().isEmpty() ? "" : autoSelector.getProfiles().get(0).name());

	private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());
	//</editor-fold>

	@Override
	public void autonomousSequence() {
		drivetrain.brakeMode();
		drivetrain.zeroHeading();
		lightBar.set(LightBar.Profile.AUTO);

		scheduler.scheduleDefaultCommand(
				new HoldElevator(elevator),
				TaskPersistence.GAMEPLAY
		);

		NetworkTables.SetPersistence(autoPublisher.getTopic(), true);
		String autoProfile = autoSubscriber.get();
		var autoCommand = autoSelector.select(autoProfile);
		scheduler.scheduleAutoCommand(autoCommand);
	}

	@Override
	public void teleopSequence() {
		drivetrain.brakeMode();
		scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera, lightBar), TaskPersistence.EPHEMERAL);
		lightBar.set(LightBar.Profile.IDLE_NO_PIECE);

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
						lightBar,
						operatorController.LEFT_TRIGGER,
						operatorController.RIGHT_TRIGGER),
				TaskPersistence.GAMEPLAY
		);

		scheduler.scheduleDefaultCommand(
				new HoldElevator(elevator),
				TaskPersistence.GAMEPLAY
		);
		//</editor-fold>

		//<editor-fold desc="Teleop Automation">
		driverController.LEFT_BUMPER.whileHeldOnce(DeferredTask.fromCommand(scheduler, () ->
						TeleopCommandGenerator.generateCommand(
								camera,
								drivetrain,
								elevator,
								intake,
								lBork,
								lightBar,
								driverController,
								operatorController,
								false
						)),
				TaskPersistence.EPHEMERAL
		);

		driverController.RIGHT_BUMPER.whileHeldOnce(DeferredTask.fromCommand(scheduler, () ->
						TeleopCommandGenerator.generateCommand(
								camera,
								drivetrain,
								elevator,
								intake,
								lBork,
								lightBar,
								driverController,
								operatorController,
								true
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
//		operatorController.RIGHT_BUMPER.whenPressed(
//				new CaptureScoringPosition(
//						operatorController.LEFT_Y_AXIS,
//						operatorController.RIGHT_X_AXIS),
//				TaskPersistence.EPHEMERAL
//		);

		operatorController.RIGHT_BUMPER.whenPressed(DeferredTask.fromCommand(scheduler, () ->
				new ManualScoringPosition(
						operatorController.LEFT_Y_AXIS,
						operatorController.RIGHT_X_AXIS,
						elevator,
						lBork,
						intake
				)),
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

		operatorController.B.whileHeld(new
				RunLBorkYankee(lBork, false),
				TaskPersistence.EPHEMERAL
		);

//		operatorController.START.whenPressed(
//				new HomeElevator(intake, lBork, elevator),
//				TaskPersistence.EPHEMERAL
//		);

		operatorController.START.whenPressed(
				DeferredTask.fromCommand(scheduler, () ->
				new SequentialCommandGroup(
						switch (ScoringPosition.targetPosition) {
							case HIGH_LEFT_YANKEE, HIGH_PAPA, HIGH_RIGHT_YANKEE -> new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_RAISED_POSITION, false, true);
							case MIDDLE_LEFT_YANKEE, MIDDLE_PAPA, MIDDLE_RIGHT_YANKEE -> new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_MID_POSITION, false, false);
							case HYBRID_LEFT, HYBRID_RIGHT -> new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_IDLE_POSITION, false, false);
							case HYBRID_MIDDLE -> new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_PAPA_POSITION, true, false);
						}
				)),
				TaskPersistence.EPHEMERAL
		);

//		operatorController.BACK.whileHeld(
//				DeferredTask.fromCommand(scheduler, () ->
//				new SequentialCommandGroup(
//						switch (ScoringPosition.targetPosition) {
//							case HIGH_LEFT_YANKEE, HIGH_RIGHT_YANKEE, MIDDLE_LEFT_YANKEE, MIDDLE_RIGHT_YANKEE, HYBRID_LEFT, HYBRID_RIGHT -> new RunLBorkYankee(lBork, true);
//							case HIGH_PAPA, MIDDLE_PAPA, HYBRID_MIDDLE -> new InstantCommand(() -> {});
//						}
//				)),
//				TaskPersistence.EPHEMERAL
//		);

		operatorController.BACK.whileHeld(
				new RunLBorkYankee(lBork, true),
				TaskPersistence.EPHEMERAL
		);
		//</editor-fold>
	}

	@Override
	public void testSequence() {
		lightBar.set(LightBar.Profile.TEST);
		drivetrain.coastMode();

		scheduler.scheduleDefaultCommand(new DriveLooped(
						drivetrain,
						driverController.LEFT_Y_AXIS,
						driverController.RIGHT_Y_AXIS,
						driverController.RIGHT_TRIGGER,
						driverController.LEFT_TRIGGER),
				TaskPersistence.EPHEMERAL
		);

		scheduler.scheduleDefaultCommand(
				new HoldElevator(elevator),
				TaskPersistence.GAMEPLAY
		);

		operatorController.LEFT_Y_AXIS.button().whileHeld(
				new MoveElevatorManual(elevator, operatorController.LEFT_Y_AXIS),
				TaskPersistence.EPHEMERAL
		);

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
