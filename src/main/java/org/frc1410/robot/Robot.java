package org.frc1410.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.button.Button;

public class Robot extends PhaseDrivenRobot {

	private final XboxController driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER);

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }

    @Override
    public void teleopSequence() {
        Button.X.bind(driverController);
    }
}
