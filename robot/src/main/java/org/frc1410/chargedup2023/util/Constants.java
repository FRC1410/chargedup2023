package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

import java.util.List;

public interface Constants {

	enum ScoringPosition {
		HIGH_LEFT_YANKEE(1, 1),
		HIGH_PAPA(0, 1),
		HIGH_RIGHT_YANKEE(-1, 1),

		MIDDLE_LEFT_YANKEE(1, 0),
		MIDDLE_PAPA(0, 0),
		MIDDLE_RIGHT_YANKEE(-1, 0),

		HYBRID_LEFT(1, -1),
		HYBRID_MIDDLE(0, -1),
		HYBRID_RIGHT(-1, -1);

		public final int x;
		public final int y;

		public static ScoringPosition targetPosition = HYBRID_MIDDLE;

		ScoringPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public static ScoringPosition fromCoords(int x, int y) {
			for (ScoringPosition position : values()) {
				if (position.x == x && position.y == y) {
					return position;
				}
			}

			throw new IllegalStateException("No matching position for (" + x + ", " + y + ")");
		}
	}
	
	int DRIVER_CONTROLLER = 0;
	int OPERATOR_CONTROLLER = 1;

	// DRIVETRAIN
//	double KS = 0.097;
//	double KV = 3.015;
//	double KA = 1.017;
	double KS = 0.6274;
	double KV = 2.265;
	double KA = 0.7535;

	double KS_SLOW = 0;
	double KV_SLOW = 0;
	double KA_SLOW = 0;

	double GEARING = 1 / ((52.0 / 10.0) * (68.0 / 30.0));
	double METERS_PER_REVOLUTION = .478778;
	double ODOMETRY_OFFSET = 0.989;
	double DRIVETRAIN_ENCODER_CONSTANT = METERS_PER_REVOLUTION * GEARING * ODOMETRY_OFFSET;
	double TRACKWIDTH = 0.614; //Meters
	DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

	// LBork rollers
	double LBORK_YANKEE_INTAKE_SPEED = 0.3; // 0.6
	double LBORK_YANKEE_OUTTAKE_SPEED = -1; // -0.3

	double LBORK_PAPA_INTAKE_SPEED = 0.7; // 0.6
	double LBORK_PAPA_OUTTAKE_SPEED = -0.5; // -0.3

	// AprilTags & Vision
	Matrix<N3, N1> VISION_STD_DEVS = VecBuilder.fill(0.3, 0.3, 0.3);
	List<Integer> SCORING_TAGS = List.of(1, 2, 3, 6, 7, 8);
	List<Integer> SUBSTATION_TAGS = List.of(4, 5);
	List<Integer> RED_TAGS = List.of(1, 2, 3, 4);
	double FIELD_WIDTH = Units.inchesToMeters(315.5);

	double WAYPOINT_THRESHOLD = -Units.inchesToMeters(70);

	// Elevator
	double ELEVATOR_SPEED = -0.5;
	double ELEVATOR_HOMING_SPEED = 0.5;


	double ELEVATOR_ENCODER_CONSTANT = 0.0013427734375;

	double ELEVATOR_NEO_ENCODER_CONSTANT = 0.0916666666667;

	double ELEVATOR_DOWN_POSITION = 0;
	double ELEVATOR_DRIVING_POSITION = 0.4;
	double ELEVATOR_IDLE_POSITION = 7.5;
	// ELEVATOR IDLE POSITION IS A GUESS, NEEDS TO BE TESTED
	double ELEVATOR_PAPA_POSITION = 3.21;
	double ELEVATOR_MID_POSITION = 23;
	double ELEVATOR_RAISED_POSITION = 31.5;
	double ELEVATOR_SUBSTATION_POSITION = 27.36;
	double ELEVATOR_TOLERANCE = 0.2;

	// Other stuff
	double INTAKE_LBORK_EXTEND_TIME = 1; // Seconds
	double OUTTAKE_TIME = 0.5; // Seconds
	double SUBSTATION_INTAKE_TIME = 3; // Seconds
	double CREEPY_WAIT = 3; // Seconds

	double RUN_LBORK_SCORING_TIME = 3; // Seconds
}
