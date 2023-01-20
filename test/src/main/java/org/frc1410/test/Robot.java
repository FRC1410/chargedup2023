package org.frc1410.test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.*;
import org.frc1410.test.commands.*;
import org.frc1410.test.commands.groups.auto.*;
import org.frc1410.test.subsystem.*;
import org.frc1410.test.util.Networktables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.impl.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.test.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final VerticalStorage verticalStorage = new VerticalStorage();
    private final LimeLight limeLight = new LimeLight();

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
            .add("Test 1 Meter", () -> new Test1MeterAuto(drivetrain))
            .add("Test 2 Meter", () -> new Test2MeterAuto(drivetrain))
            .add("Test SCurve Nx0 Short", () -> new TestSCurveNx0Short(drivetrain))
            .add("Test SCurve Nx0 Long", () -> new TestSCurveNx0Long(drivetrain))
            .add("Test SCurve 1x1 Short", () -> new TestSCurve1x1Short(drivetrain))
            .add("Test SCurve 1x1 Long", () -> new TestSCurve1x1Long(drivetrain))
            .add("Test SCurve 1x2 Short", () -> new TestSCurve1x2Short(drivetrain))
            .add("Test SCurve 1x2 Long", () -> new TestSCurve1x2Long(drivetrain))
            .add("Test Arc 60 Short", () -> new TestArc60Short(drivetrain))
            .add("Test Arc 60 Long", () -> new TestArc60Long(drivetrain))
            .add("Test Arc 180 Short", () -> new TestArc180Short(drivetrain))
            .add("Test Arc 180 Long", () -> new TestArc180Long(drivetrain))
            // REAL TRAJECTORIES
            .add("Mobility", () -> new MobilityAuto(drivetrain))
            .add("Barrier Community To Game Piece", () -> new BarrierCommunityToGamePieceAuto(drivetrain))
            .add("Barrier Community To Game Piece To Charging Station", () -> new BarrierCommunityToGamePieceToChargingStationAuto(drivetrain))
            .add("Outside Community To Game Piece", () -> new OutsideCommunityToGamePieceAuto(drivetrain))
            .add("Outside Community To Game Piece To Charging Station", () -> new OutsideCommunityToGamePieceToRechargeStationAuto(drivetrain));
    private final StringPublisher autoPublisher = Networktables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = Networktables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();
        var autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleAutoCommand(autoCommand);
        System.out.println("Auto Done");
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.RIGHT_X_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
        scheduler.scheduleDefaultCommand(new RunIntake(intake, driverController.LEFT_TRIGGER), TaskPersistence.GAMEPLAY);

        driverController.RIGHT_BUMPER.whenPressed(new CommandTask(new SwitchDriveMode(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.LEFT_BUMPER.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.A.whileHeld(new CommandTask(new Shoot(shooter, verticalStorage)), TaskPersistence.EPHEMERAL);

        driverController.X.whenPressed(new CommandTask(new SwitchDriveMode(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.Y.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);

        driverController.B.whenPressed(new CommandTask(new ToggleLimeLightModeAction(limeLight)), TaskPersistence.EPHEMERAL);

    }

    @Override
    public void testSequence() {
        drivetrain.resetPoseEstimation(new Pose2d(0,0,new Rotation2d(0)));
        drivetrain.zeroHeading();
        drivetrain.coastMode();
    }
}