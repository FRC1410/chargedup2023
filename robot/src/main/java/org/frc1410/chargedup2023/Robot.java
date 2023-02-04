package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.*;
import org.frc1410.chargedup2023.commands.SwitchDriveMode;
import org.frc1410.chargedup2023.commands.DriveLooped;
import org.frc1410.chargedup2023.commands.FlipDrivetrainAction;
import org.frc1410.chargedup2023.commands.groups.auto.*;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control2.Controller;
import org.frc1410.framework.scheduler.task.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
            .add("Barrier Scoring To Charging Station", () -> new BarrierScoringToChargingStation(drivetrain))
            .add("GO", () -> new BarrierGamePieceToScore(drivetrain));

    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

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
        driverController.RIGHT_BUMPER.whenPressed(new CommandTask(new SwitchDriveMode(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
        driverController.LEFT_BUMPER.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
    }


    @Override
    public void testSequence() {
        drivetrain.coastMode();
    }
}