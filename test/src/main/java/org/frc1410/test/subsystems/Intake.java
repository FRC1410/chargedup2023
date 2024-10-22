package org.frc1410.test.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.framework.scheduler.subsystem.TickedSubsystem;

public class Intake implements TickedSubsystem, Subsystem {

	private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(10);

	public void setIntakeSpeed(double speed) {
		intakeMotor.set(speed);
	}

	public Intake() {
		// TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
		//	   in the constructor or in the robot coordination class, such as RobotContainer.
	}

	@Override
	public void periodic() {

	}
}

