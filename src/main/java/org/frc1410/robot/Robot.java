package org.frc1410.robot;

import edu.wpi.first.wpilibj.RobotBase;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control2.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.robot.command.RunIntake;
import org.frc1410.robot.subsystem.Intake;

public class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, Constants.DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, Constants.OPERATOR_CONTROLLER);

    private final Intake intake = subsystems.track(new Intake());

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }

    @Override
    public void teleopSequence() {
        driverController.A.whenPressed(new RunIntake(intake), TaskPersistence.GAMEPLAY);
    }
}

