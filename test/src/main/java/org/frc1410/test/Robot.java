package org.frc1410.test;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.frc1410.test.commands.*;
import org.frc1410.test.commands.GoToAprilTag;
import org.frc1410.test.commands.groups.auto.barrier.Barrier2YankeeEngage;
import org.frc1410.test.commands.groups.auto.barrier.BarrierCommunityToGamePiece;
import org.frc1410.test.commands.groups.auto.barrier.BarrierGamePieceToScore;
import org.frc1410.test.commands.groups.auto.outside.OutsideCommunityToGamePiece;
import org.frc1410.test.commands.groups.auto.outside.OutsideGamePieceToScore;
import org.frc1410.test.subsystems.*;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.test.util.NetworkTables;

import static org.frc1410.test.auto.POIs.BARRIER_GAME_PIECE_FORWARD;
import static org.frc1410.test.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER, 0.12);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER, 0.25);

    private final ExternalCamera camera = subsystems.track(new ExternalCamera());
    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final VerticalStorage verticalStorage = new VerticalStorage();

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
            // REAL TRAJECTORIES
            .add("Barrier Community To Game Piece", () -> new BarrierCommunityToGamePiece(drivetrain))
            .add("Outside Community To Game Piece", () -> new OutsideCommunityToGamePiece(drivetrain))
			.add("Barrier Game Piece To Score", () -> new BarrierGamePieceToScore(drivetrain))
			.add("Outside Game Piece To Score", () -> new OutsideGamePieceToScore(drivetrain))
			.add("2 Yankee Engage", () -> new Barrier2YankeeEngage(drivetrain));

    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

	DoublePublisher gyroPub = NetworkTables.PublisherFactory(table, "Gyro", 0);
	DoubleSubscriber gyroSub = NetworkTables.SubscriberFactory(table, gyroPub.getTopic());

    public Robot() {
        drivetrain.zeroHeading();
		drivetrain.coastMode();
    }

	@Override
	public void disabledSequence() {
		drivetrain.coastMode();
	}

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();

        NetworkTables.SetPersistence(autoPublisher.getTopic(), true);
        String autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleAutoCommand(autoCommand);
    }

    @Override
    public void teleopSequence() {
		drivetrain.zeroHeading();
        drivetrain.coastMode();
        scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera), TaskPersistence.EPHEMERAL);
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
//        scheduler.scheduleDefaultCommand(new RunIntake(intake, driverController.LEFT_TRIGGER), TaskPersistence.GAMEPLAY);

        driverController.A.whenPressed(new GoToAprilTag(drivetrain, camera, GoToAprilTag.Node.LEFT_YANKEE_NODE, scheduler), TaskPersistence.EPHEMERAL);
        driverController.X.whileHeld(new DetectAprilTag(camera, driverController), TaskPersistence.EPHEMERAL);
		drivetrain.resetPoseEstimation(BARRIER_GAME_PIECE_FORWARD);
		driverController.Y.whenPressed(new InstantCommand(() -> drivetrain.zeroHeading(gyroSub.get())), TaskPersistence.EPHEMERAL);

    }

    @Override
    public void testSequence() {
        drivetrain.zeroHeading();
        drivetrain.coastMode();

        scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera), TaskPersistence.EPHEMERAL);
    }
}
