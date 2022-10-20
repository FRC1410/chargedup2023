package org.frc1410.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Button;
import org.frc1410.framework.flow.PhaseSequencer;

public class Robot extends PhaseDrivenRobot {


	private final XboxController driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER);


	@Override
	public void disabledSequence(PhaseSequencer seq) {
        new Button(driverController, Button.X);
        /*
        new Button(driverController, Button.X).whileHeld()
         */

		// whenPressed: new ClickButton(ctrl, Button.X)
		// whileHeld: new HoldButton(ctrl, Button.X)
		// toggleWhenPressed: new ToggleButton(ctrl, Button.X)

		/*
		new ClickButton(ctrl, Button.X)
			.bind(new MyCommand(dep1, dep2));
		 */

		/*
		// whenPressed
		driverController.bind(Button.X)
			.launchOnHold()
			.cancelOnRelease()
			.attach(seq);

		// whileHeld
		driverController.bind(Button.X)
			.launchOnHold()
			.loop()
			.cancelOnRelease();

		// toggleWhenPressed
		driverController.bind(Button.X)
			.toggle();

		driverController.bind(Button.X)
			.
		 */

	}

    public static void main(String[] args) {
        RobotBase.startRobot(Robot::new);
    }
}
