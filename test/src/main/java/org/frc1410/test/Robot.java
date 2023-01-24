package org.frc1410.test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.test.commands.*;
import org.frc1410.test.commands.groups.auto.BarrierCommunityToGamePiece;
import org.frc1410.test.subsystem.Drivetrain;
import org.frc1410.test.subsystem.Intake;
import org.frc1410.test.subsystem.Shooter;
import org.frc1410.test.subsystem.VerticalStorage;
import org.frc1410.test.util.NetworkTables;

import static org.frc1410.test.util.Constants.DRIVER_CONTROLLER;
import static org.frc1410.test.util.Constants.OPERATOR_CONTROLLER;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final VerticalStorage verticalStorage = new VerticalStorage();

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
            // REAL TRAJECTORIES
            .add("Barrier Community To Game Piece", () -> new BarrierCommunityToGamePiece(drivetrain))
            .add("Barrier Community To Game Piece", () -> new BarrierCommunityToGamePiece(drivetrain));
    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            /*autoSelector.getProfiles().get(0).name()*/""); // uncomment when profiles are available
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();

        NetworkTables.SetPersistence(autoPublisher.getTopic(), true);
        String autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleAutoCommand(autoCommand);
        System.out.println("Auto Done");
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
        scheduler.scheduleDefaultCommand(new RunIntake(intake, driverController.LEFT_TRIGGER), TaskPersistence.GAMEPLAY);

        driverController.RIGHT_BUMPER.whenPressed(new SwitchDriveMode(drivetrain, driverController), TaskPersistence.EPHEMERAL);
        driverController.LEFT_BUMPER.whenPressed(new FlipDrivetrainAction(drivetrain, driverController), TaskPersistence.EPHEMERAL);
        driverController.A.whileHeld(new Shoot(shooter, verticalStorage), TaskPersistence.EPHEMERAL);
    }

    @Override
    public void testSequence() {
        drivetrain.resetPoseEstimation(new Pose2d(0,0,new Rotation2d(0)));
        drivetrain.zeroHeading(); // X
        drivetrain.coastMode(); // X
    }
}