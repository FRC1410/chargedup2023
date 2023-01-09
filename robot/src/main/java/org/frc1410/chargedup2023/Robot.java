package org.frc1410.chargedup2023;

import org.frc1410.chargedup2023.commands.groups.auto.MobilityAuto;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.AutoSelector;

public final class Robot extends PhaseDrivenRobot {

    // private final Drivetrain drivetrain = new Drivetrain();

    private final AutoSelector autoSelector = new AutoSelector()
        .add("mobility", new MobilityAuto());

    @Override
    public void teleopSequence() {
        System.out.println("Teleop!!");
    }

    @Override
    public void autonomousSequence() {

    }
}