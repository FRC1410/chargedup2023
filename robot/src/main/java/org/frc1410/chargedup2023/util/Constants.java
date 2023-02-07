package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

import java.util.ArrayList;
import java.util.List;

public interface Constants {

	enum ScoringPosition {
		HIGH_LEFT_CONE(1, 1),
		HIGH_CUBE(0, 1),
		HIGH_RIGHT_CONE(-1, 1),

		MIDDLE_LEFT_CONE(1, 0),
		MIDDLE_CUBE(0, 0),
		MIDDLE_RIGHT_CONE(-1, 0),

		HYBRID_LEFT(1, -1),
		HYBRID_MIDDLE(0, -1),
		HYBRID_RIGHT(-1, -1);

		public final int x;
		public final int y;

		public static ScoringPosition targetPosition;

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
	double KS = 0;
	double KV = 0;
	double KA = 0;

	double KS_SLOW = 0;
	double KV_SLOW = 0;
	double KA_SLOW = 0;

	double METERS_PER_REVOLUTION = .478778;
	double TRACKWIDTH = 0.615; //Meters
	DifferentialDriveKinematics KINEMATICS = new DifferentialDriveKinematics(TRACKWIDTH);

	// LBork rollers
	double LBORK_CONE_INTAKE_SPEED = 0.6;
	double LBORK_CONE_OUTTAKE_SPEED = 0.3;

	double LBORK_CUBE_INTAKE_SPEED = 0.6;
	double LBORK_CUBE_OUTTAKE_SPEED = 0.3;

	// AprilTags
	List<Integer> RED_TAGS = new ArrayList<>(List.of(1, 2, 3, 4));
	List<Integer> BLUE_TAGS = new ArrayList<>(List.of(5, 6, 7, 8));
	double WAYPOINT_THRESHOLD = -Units.inchesToMeters(70);

	// Elevator
	double ELEVATOR_SPEED = 0.5;
	double ELEVATOR_HOMING_SPEED = -0.25;

	double ELEVATOR_DOWN_POSITION = 0;
	double ELEVATOR_DRIVING_POSITION = 0;
	double ELEVATOR_CUBE_POSITION = 0;
	double ELEVATOR_MID_POSITION = 0;
	double ELEVATOR_RAISED_POSITION = 0;
	double ELEVATOR_TOLERANCE = 0.1;

	// Other stuff
	double INTAKE_LBORK_EXTEND_TIME = 0.25; // Seconds
	double SUBSTATION_INTAKE_TIME = 3; // Seconds
}
