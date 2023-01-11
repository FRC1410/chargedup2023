package org.frc1410.chargedup2023;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.RunCommand;
import org.frc1410.chargedup2023.commands.TankDriveLooped;
import org.frc1410.chargedup2023.commands.FlipDrivetrainAction;
import org.frc1410.chargedup2023.commands.groups.auto.Test1MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test2MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test90DegreeAuto;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Networktables;
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
            .add("Test 1 Meter", () -> new Test1MeterAuto(drivetrain))
            .add("Test 2 Meter", () -> new Test2MeterAuto(drivetrain));

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
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new TankDriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS), TaskPersistence.GAMEPLAY);
        driverController.LEFT_BUMPER.whenPressed(new CommandTask(new FlipDrivetrainAction(drivetrain, driverController)), TaskPersistence.EPHEMERAL);
//        driverController.A.whenPressed(new CommandTask(new RunCommand(drivetrain::zeroHeading)), TaskPersistence.EPHEMERAL);
    }


    @Override
    public void testSequence() {
        drivetrain.coastMode();
    }
}