package org.frc1410.chargedup2023;

import edu.wpi.first.wpilibj2.command.RunCommand;
import org.frc1410.chargedup2023.commands.groups.auto.MobilityAuto;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;

public final class Robot extends PhaseDrivenRobot {


    // private final Drivetrain drivetrain = new Drivetrain();

    @Override
    public void teleopSequence() {
        System.out.println("Teleop!!");
    }

    @Override
    public void autonomousSequence() {
        var command = new RunCommand(() -> System.out.println("Hello world"));
        controller.A.whenPressed(command, TaskPersistence.GAMEPLAY);
    }
}