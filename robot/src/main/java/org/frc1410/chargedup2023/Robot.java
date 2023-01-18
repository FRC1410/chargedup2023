package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import org.frc1410.chargedup2023.commands.*;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.impl.CommandTask;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector();

    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();
        String autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleDefaultCommand(autoCommand, TaskPersistence.EPHEMERAL);
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.RIGHT_X_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);

        driverController.LEFT_BUMPER.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.RIGHT_BUMPER.whenPressed(new CommandTask(new SwitchDriveMode(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
    }

    @Override
    public void testSequence() {
        drivetrain.coastMode();
    }
}