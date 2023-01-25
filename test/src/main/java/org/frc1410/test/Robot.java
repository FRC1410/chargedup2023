package org.frc1410.test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.*;
import org.frc1410.test.commands.*;
import org.frc1410.test.commands.groups.GoToAprilTag;
import org.frc1410.test.subsystems.*;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.impl.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import java.io.IOException;

import static org.frc1410.test.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

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
            .add("Game Piece To Barrier Community", () -> new GamePieceToBarrierCommunity(drivetrain))
            .add("Outside Community To Game Piece", () -> new OutsideCommunityToGamePiece(drivetrain))
            .add("Game Piece To Outside Community", () -> new GamePieceToOutsideCommunity(drivetrain));
    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            /*autoSelector.getProfiles().get(0).name()*/""); // uncomment when profiles are available
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

    public Robot() throws IOException {
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
//        drivetrain.brakeMode();
        drivetrain.coastMode();
        scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera), TaskPersistence.EPHEMERAL);
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
        scheduler.scheduleDefaultCommand(new RunIntake(intake, driverController.LEFT_TRIGGER), TaskPersistence.GAMEPLAY);

        driverController.RIGHT_BUMPER.whenPressed(new SwitchDriveMode(drivetrain, driverController), TaskPersistence.EPHEMERAL);
        driverController.LEFT_BUMPER.whenPressed(new FlipDrivetrainAction(drivetrain, driverController), TaskPersistence.EPHEMERAL);
        driverController.A.whileHeld(new Shoot(shooter, verticalStorage), TaskPersistence.EPHEMERAL);

        driverController.X.whileHeld(new DetectAprilTag(camera, driverController), TaskPersistence.EPHEMERAL);
    }

    @Override
    public void testSequence() {
        drivetrain.resetPoseEstimation(new Pose2d(Units.inchesToMeters(82),0, new Rotation2d(0)));
        drivetrain.zeroHeading();
        drivetrain.coastMode();

        scheduler.scheduleDefaultCommand(new UpdatePoseEstimation(drivetrain, camera), TaskPersistence.EPHEMERAL);

        driverController.A.whenPressed(new GoToAprilTag(drivetrain, camera), TaskPersistence.EPHEMERAL);
    }
}