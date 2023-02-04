package org.frc1410.chargedup2023.util;

public interface IDs {

    public static final int[] LEFT_ENCODER_PORTS = new int[] {0, 1};
    public static final int[] RIGHT_ENCODER_PORTS = new int[] {2, 3};

	// Drivetrain ID's
	int DRIVETRAIN_LEFT_FRONT_MOTOR_ID = 1;
	int DRIVETRAIN_RIGHT_FRONT_MOTOR_ID = 2;
	int DRIVETRAIN_LEFT_BACK_MOTOR_ID = 3;
	int DRIVETRAIN_RIGHT_BACK_MOTOR_ID = 4;

	// Intake ID's
	int INTAKE_MOTOR_ID = 5;

	int LBORK_OUTER_ROLLER_ID = 6;
	int LBORK_INNER_ROLLER_ID = 7;

	// Elevator
	int ELEVATOR_MOTOR_ONE_ID = 8;
	int ELEVATOR_MOTOR_TWO_ID = 9;

	// Intake pistons ID's
	int INTAKE_FLIPPER_FOWARD_ID = 0;
	int INTAKE_FLIPPER_BACKWARD_ID = 1;

	// Lebork piston ID's
	int LBORK_PISTON_FOWARD_ID = 2;
	int LBORK_PISTON_BACKWARDS_ID = 3;
}
