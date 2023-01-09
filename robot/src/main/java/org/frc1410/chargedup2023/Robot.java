package org.frc1410.chargedup2023;

import org.frc1410.chargedup2023.commands.TankDriveLooped;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control2.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    @Override
    public void teleopSequence() {
        scheduler.scheduleDefaultCommand(new TankDriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS), TaskPersistence.GAMEPLAY);
    }
}