package org.frc1410.robot.subsystem;

import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

public class Intake implements TickedSubsystem {

    @Override
    public void periodic() {
        System.out.println("Intake periodic tick");
    }
}