package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

import java.util.List;

public interface Constants {

	//<editor-fold desc="Scoring Position">
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

	public enum Node {
		LEFT_YANKEE_NODE,
		PAPA_NODE,
		RIGHT_YANKEE_NODE,
		LEFT_SUBSTATION,
		RIGHT_SUBSTATION
	}
	//</editor-fold>

	//<editor-fold desc="Controllers">
	int DRIVER_CONTROLLER = 0;
	int OPERATOR_CONTROLLER = 1;
	//</editor-fold>

	//<editor-fold desc="Drivetrain">
	// DRIVETRAIN
//	double KS = 0.097;
//	double KV = 3.015;
//	double KA = 1.017;
	double DRIVETRAIN_GRID_SPEED = 0.3;
	double KS_AUTO = 0.6274;
	double KV_AUTO = 2.265;
	double KA_AUTO = 0.7535;

	double KS_TELEOP = 0.5274;
	double KV_TELEOP = 2.165;
	double KA_TELEOP = 0.3535;

	double GEARING = 1 / ((52.0 / 10.0) * (68.0 / 30.0));
	double METERS_PER_REVOLUTION = .478778;
	double ODOMETRY_OFFSET = 0.989;
	double DRIVETRAIN_ENCODER_CONSTANT = METERS_PER_REVOLUTION * GEARING * ODOMETRY_OFFSET;
	double TRACKWIDTH = 0.614; //Meters
	DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);
	//</editor-fold>

	//<editor-fold desc="LBork">
	// LBork rollers
	double LBORK_YANKEE_INTAKE_OUTER_ROLLER_SPEED = 1;
	double LBORK_YANKEE_INTAKE_INNER_ROLLER_SPEED = -0.3;
	double LBORK_YANKEE_OUTTAKE_SPEED = 1;

	double LBORK_PAPA_OUTTAKE_OUTER_ROLLER_SPEED = -1;
	double LBORK_PAPA_OUTTAKE_INNER_ROLLER_SPEED = -1;

	double LBORK_PAPA_INTAKE_OUTER_ROLLER_SPEED = 1;
	double LBORK_PAPA_INTAKE_INNER_ROLLER_SPEED = 1;

	double LBORK_PAPA_INTAKE_OFFSET_TIME = 0.5;
	//</editor-fold>

	//<editor-fold desc="AprilTags & Vision">
	// AprilTags & Vision
	Matrix<N3, N1> VISION_STD_DEVS = VecBuilder.fill(0.3, 0.3, 0.3);
	List<Integer> SUBSTATION_TAGS = List.of(4, 5);
	List<Integer> SCORING_TAGS = List.of(1, 2, 3, 6, 7, 8);
	List<Integer> RED_TAGS = List.of(1, 2, 3, 5);
	double FIELD_WIDTH = Units.inchesToMeters(315.5);

	//</editor-fold>

	//<editor-fold desc="Elevator">
	// Elevator
	double ELEVATOR_HOMING_SPEED = 0.3;

	double ELEVATOR_NEO_ENCODER_CONSTANT = -0.0916666666667 * 3;

	double ELEVATOR_INTAKE_INTERFERENCE_HEIGHT = 6.2; // Inches

	double ELEVATOR_DOWN_POSITION = 0;
	double ELEVATOR_DRIVING_POSITION = 0.4;
	double ELEVATOR_IDLE_POSITION = 9.5;
	double ELEVATOR_PAPA_POSITION = 1.9;
	double ELEVATOR_MID_POSITION = 22.5;
	double ELEVATOR_HYBRID_POSITION = 18.5;
	double ELEVATOR_RAISED_POSITION = 30.5;
	double ELEVATOR_SUBSTATION_POSITION = 31;
	double ELEVATOR_TOLERANCE = 0.2;

	double ELEVATOR_HOLDING_POWER = -0.02; // Percent
	//</editor-fold>

	//<editor-fold desc="Timing">
	double INTAKE_LBORK_EXTEND_TIME = 0.5; // Seconds
	double YANKEE_OUTTAKE_TIME = 0.7; // Seconds
	double PAPA_OUTTAKE_TIME = 2; // Seconds
	double SUBSTATION_INTAKE_OFFSET_TIME = 0.6; // Seconds
	//</editor-fold>

	//<editor-fold desc="Auto">
	double OKLAHOMA_WAIT = 3; // Seconds
	double CREEPY_SPEED = 5; // Volts
	double CREEPY_TIME = 1.7;
	// </editor-fold>
}

