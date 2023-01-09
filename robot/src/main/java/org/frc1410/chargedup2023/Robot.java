package org.frc1410.chargedup2023;

import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control2.Controller;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, Constants.DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, Constants.OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    @Override
    public void teleopSequence() {
        System.out.println("Teleop!!");
    }
}