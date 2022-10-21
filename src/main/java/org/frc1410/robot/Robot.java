package org.frc1410.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.PhaseDrivenRobot;

public class Robot extends PhaseDrivenRobot {


	private final XboxController driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER);

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }
    
}
