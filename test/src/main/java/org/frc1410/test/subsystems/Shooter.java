package org.frc1410.test.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Shooter implements Subsystem {
	
	private final WPI_TalonFX motor = new WPI_TalonFX(30);

	public Shooter() {
		// TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
		//	   in the constructor or in the robot coordination class, such as RobotContainer.
	}

	public void setSpeed(double speed) {
		motor.set(speed);
	}
}

