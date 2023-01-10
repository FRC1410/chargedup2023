package org.frc1410.chargedup2023;

import org.frc1410.chargedup2023.commands.TankDriveLooped;
import org.frc1410.chargedup2023.commands.groups.auto.Test1MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test2MeterAuto;
import org.frc1410.chargedup2023.commands.groups.auto.Test90DegreeAuto;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control2.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    private final AutoSelector autoSelector = new AutoSelector()
            .add("Test 90 Degrees", new Test90DegreeAuto(drivetrain))
            .add("Test 1 Meter", new Test1MeterAuto(drivetrain))
            .add("Test 2 Meter", new Test2MeterAuto(drivetrain));

    @Override
    public void teleopSequence() {
        scheduler.scheduleDefaultCommand(new TankDriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS), TaskPersistence.GAMEPLAY);
    }
}