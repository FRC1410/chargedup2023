package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.*;
import org.frc1410.chargedup2023.commands.*;
import org.frc1410.chargedup2023.commands.groups.auto.MobilityAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test1MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test2MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.TestQuarterCircleAuto;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.subsystem.Intake;
import org.frc1410.chargedup2023.subsystem.Shooter;
import org.frc1410.chargedup2023.subsystem.VerticalStorage;
import org.frc1410.chargedup2023.util.Networktables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

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
            .add("Test 1 Meter", () -> new Test1MeterAuto(drivetrain))
            .add("Test 2 Meter", () -> new Test2MeterAuto(drivetrain))
            .add("Test Quarter Circle", () -> new TestQuarterCircleAuto(drivetrain))
            .add("Mobility", () -> new MobilityAuto(drivetrain));

    private final StringPublisher autoPublisher = Networktables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = Networktables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();
        var autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleDefaultCommand(autoCommand, TaskPersistence.EPHEMERAL);
        System.out.println("Auto Done");
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.RIGHT_X_AXIS), TaskPersistence.GAMEPLAY);
        scheduler.scheduleDefaultCommand(new RunIntake(intake, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
        driverController.RIGHT_BUMPER.whenPressed(new CommandTask(new SwitchDriveMode(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.LEFT_BUMPER.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.A.whileHeld(new CommandTask(new Shoot(shooter, verticalStorage)), TaskPersistence.EPHEMERAL);
    }

    @Override
    public void testSequence() {
        drivetrain.coastMode();
    }
}