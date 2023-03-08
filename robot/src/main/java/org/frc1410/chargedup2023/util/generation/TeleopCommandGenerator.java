package org.frc1410.chargedup2023.util.generation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import org.frc1410.chargedup2023.commands.actions.ResetDrivetrain;
import org.frc1410.chargedup2023.commands.actions.drivetrain.TurnToSmallAngle;
import org.frc1410.chargedup2023.commands.actions.intake.RunIntake;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkPapa;
import org.frc1410.chargedup2023.commands.actions.lbork.RunLBorkYankee;
import org.frc1410.chargedup2023.commands.groups.teleop.OTFToPoint;
import org.frc1410.chargedup2023.commands.actions.SetSuperStructurePosition;
import org.frc1410.chargedup2023.subsystems.*;
import org.frc1410.framework.util.log.Logger;

import java.util.ArrayList;

import static org.frc1410.chargedup2023.auto.POIs.*;
import static org.frc1410.chargedup2023.util.Constants.*;

public class TeleopCommandGenerator {

	private static final Logger generateCommandLog = new Logger("generateCommand");

	private static final Logger generateSubstationLog = new Logger("generateSubstation");
	private static final Logger generateScoringLog = new Logger("generateScoring");
	private static final Logger goToAprilTagLogger = new Logger("goToAprilTag");


	public static Command generateCommand(
			ExternalCamera camera,
			Drivetrain drivetrain,
			Elevator elevator,
			Intake intake,
			LBork lBork,
			LightBar lightBar,
			boolean rightBumper
	) {
		generateCommandLog.debug("Generator Called");
		// Get the target position from the camera
		var aprilTagPoseOptional = camera.getTargetLocation();

		// If we can't see a target, return
		if (aprilTagPoseOptional.isEmpty()) {
			generateCommandLog.error("No AprilTag found");
			return new InstantCommand();
		}

		var aprilTagPose = aprilTagPoseOptional.get();
		generateCommandLog.debug("April Tag found with position: " + aprilTagPose);

		// From here on, we assume that we have a valid target we want to act on
		var toRun = new ArrayList<Command>();

		// Now we need to check if the drivetrain has been reset
		// And if not, add that to the list of commands to run
		generateCommandLog.debug("Resetting drivetrain position");
		toRun.add(
				new ResetDrivetrain(drivetrain, camera, true)
		);

		// Now on to the tag logic itself
		var tagID = camera.getTarget().getFiducialId();
		generateCommandLog.debug("April Tag ID: " + tagID);

		// If we are looking at a substation tag
		if (SUBSTATION_TAGS.contains(tagID)) {
			// Change lights
			lightBar.set(LightBar.Profile.SUBSTATION_NO_PIECE);

			// Generate substation pickup command
			generateCommandLog.debug("Substation tag found, generating substation pickup command");
			toRun.add(substationPickupGenerator(
					drivetrain,
					lBork,
					elevator,
					intake,
					lightBar,
					rightBumper,
					aprilTagPose,
					tagID
			));
		} else if (SCORING_TAGS.contains(tagID)) {
			// Change lights
			lightBar.set(LightBar.Profile.SCORING);

			// Generate scoring command
			generateCommandLog.debug("Grid tag found, generating scoring command");
			toRun.add(scoringGenerator(
					drivetrain,
					elevator,
					intake,
					lBork,
					lightBar,
					aprilTagPose,
					tagID
			));
		}

		// Always have to reset if the drivetrain has been reset or not
		toRun.add(new InstantCommand(() -> drivetrain.setReset(false)));

		toRun.add(new RunCommand(() -> {}));

		return new SequentialCommandGroup(toRun.toArray(new Command[0]));
//		return new InstantCommand();
	}

	private static Command substationPickupGenerator(
			Drivetrain drivetrain,
			LBork lBork,
			Elevator elevator,
			Intake intake,
			LightBar lightBar,
			boolean rightBumper,
			Pose3d aprilTagPose,
			int tagID
	) {
		generateSubstationLog.debug("Function entered");
		var sublist = new ArrayList<Command>();

		generateSubstationLog.debug("Picking up from " + (rightBumper ? "right" : "left") + " side of substation");

		sublist.add(
				new SequentialCommandGroup(
						goToAprilTagGenerator(
								drivetrain,
								rightBumper ? Node.RIGHT_SUBSTATION : Node.LEFT_SUBSTATION,
								aprilTagPose,
								tagID
						),
						tagID == 5
								? new TurnToSmallAngle(drivetrain, 180)
								: new TurnToSmallAngle(drivetrain, 0),
						new ParallelRaceGroup(
								new SetSuperStructurePosition(
										elevator,
										intake,
										lBork,
										ELEVATOR_RAISED_POSITION,
										false,
										false
								),
								new RunLBorkYankee(lBork, false)
						),
						new ParallelRaceGroup(
								new RunLBorkYankee(lBork, false),
								new WaitCommand(SUBSTATION_INTAKE_TIME) // TODO: Make linebreak
						),
						new InstantCommand(() -> lightBar.set(LightBar.Profile.SUBSTATION_PIECE)),
						new SetSuperStructurePosition(elevator, intake, lBork, ELEVATOR_IDLE_POSITION, false, false),
						new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_PIECE))
				)
		);

		return new SequentialCommandGroup(sublist.toArray(new Command[0]));
	}

	private static Command goToAprilTagGenerator(
			Drivetrain drivetrain,
			Node targetNode,
			Pose3d aprilTagPose,
			int tagID
	) {
		var isRed = RED_TAGS.contains(tagID);
		var waypointFlag = isRed
				? drivetrain.getPoseEstimation().getX() < RED_OUTSIDE_WAYPOINT.getX() - Units.inchesToMeters(20)
				: drivetrain.getPoseEstimation().getX() > BLUE_OUTSIDE_WAYPOINT.getX() + Units.inchesToMeters(20);

		goToAprilTagLogger.debug("Function entered");
		goToAprilTagLogger.debug("Waypoint " + (waypointFlag ? "is necessary" : "is not necessary"));
		goToAprilTagLogger.debug((isRed ? "Red" : "Blue") + " tag found, generating accordingly");

		switch (targetNode) {
			case LEFT_YANKEE_NODE -> {
				goToAprilTagLogger.debug("Target is left yankee");
				if ((tagID == 1 || tagID == 6) && waypointFlag) {
					goToAprilTagLogger.debug("Waypoint go brrrrrr");
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed ? RED_OUTSIDE_WAYPOINT : BLUE_BARRIER_WAYPOINT,
							isRed
									? new Pose2d(
									RED_LEFT_YANKEE_NODE.getX(),
									RED_LEFT_YANKEE_NODE.getY() + Units.inchesToMeters(3),
									RED_LEFT_YANKEE_NODE.getRotation())
									: new Pose2d(
									BLUE_LEFT_YANKEE_NODE.getX(),
									BLUE_LEFT_YANKEE_NODE.getY() + Units.inchesToMeters(5),
									BLUE_LEFT_YANKEE_NODE.getRotation())
					);
				} else if (tagID == 1 || tagID == 6) {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed
									? new Pose2d(
									RED_LEFT_YANKEE_NODE.getX(),
									RED_LEFT_YANKEE_NODE.getY() + Units.inchesToMeters(3),
									RED_LEFT_YANKEE_NODE.getRotation())
									: new Pose2d(
									BLUE_LEFT_YANKEE_NODE.getX(),
									BLUE_LEFT_YANKEE_NODE.getY() + Units.inchesToMeters(5),
									BLUE_LEFT_YANKEE_NODE.getRotation())
					);
				} else if (tagID == 3 || tagID == 8) {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed
									? new Pose2d(
									RED_LEFT_YANKEE_NODE.getX(),
									RED_LEFT_YANKEE_NODE.getY() - Units.inchesToMeters(3),
									RED_LEFT_YANKEE_NODE.getRotation())
									: new Pose2d(
									BLUE_LEFT_YANKEE_NODE.getX(),
									BLUE_LEFT_YANKEE_NODE.getY() - Units.inchesToMeters(5),
									BLUE_LEFT_YANKEE_NODE.getRotation())
					);
				} else {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed ? RED_LEFT_YANKEE_NODE : BLUE_LEFT_YANKEE_NODE
					);
				}
			}

			case PAPA_NODE -> {
				goToAprilTagLogger.debug("Target is papa");
				return new OTFToPoint(
						drivetrain,
						aprilTagPose.toPose2d(),
						isRed ? RED_PAPA_NODE : BLUE_PAPA_NODE
				);
			}

			case RIGHT_YANKEE_NODE -> {
				goToAprilTagLogger.debug("Target is right yankee");
				if ((tagID == 3 || tagID == 8) && waypointFlag) {
					goToAprilTagLogger.debug("Waypoint go brrrrrr");
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed ? RED_BARRIER_WAYPOINT : BLUE_OUTSIDE_WAYPOINT,
							isRed
									? new Pose2d(
											RED_RIGHT_YANKEE_NODE.getX(),
											RED_RIGHT_YANKEE_NODE.getY() - Units.inchesToMeters(2),
											RED_RIGHT_YANKEE_NODE.getRotation())
									: new Pose2d(
											BLUE_RIGHT_YANKEE_NODE.getX(),
											BLUE_RIGHT_YANKEE_NODE.getY() - Units.inchesToMeters(2),
											BLUE_RIGHT_YANKEE_NODE.getRotation())
					);
				} else if (tagID == 3 || tagID == 8) {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed 
									? new Pose2d(
											RED_RIGHT_YANKEE_NODE.getX(),
											RED_RIGHT_YANKEE_NODE.getY() - Units.inchesToMeters(2),
											RED_RIGHT_YANKEE_NODE.getRotation())
									: new Pose2d(
											BLUE_RIGHT_YANKEE_NODE.getX(),
											BLUE_RIGHT_YANKEE_NODE.getY() - Units.inchesToMeters(2),
											BLUE_RIGHT_YANKEE_NODE.getRotation())
							);
				} else if (tagID == 1 || tagID == 6) {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed
									? new Pose2d(
											RED_RIGHT_YANKEE_NODE.getX(),
											RED_RIGHT_YANKEE_NODE.getY() + Units.inchesToMeters(2),
											RED_RIGHT_YANKEE_NODE.getRotation())
									: new Pose2d(
											BLUE_RIGHT_YANKEE_NODE.getX(),
											BLUE_RIGHT_YANKEE_NODE.getY() + Units.inchesToMeters(2),
											BLUE_RIGHT_YANKEE_NODE.getRotation())
					);
				} else {
					return new OTFToPoint(
							drivetrain,
							aprilTagPose.toPose2d(),
							isRed ? RED_RIGHT_YANKEE_NODE : BLUE_RIGHT_YANKEE_NODE
					);
				}
			}

			case LEFT_SUBSTATION -> {
				goToAprilTagLogger.debug("Target is left substation");
				return new OTFToPoint(
						drivetrain,
						aprilTagPose.toPose2d(),
						isRed ? RED_LEFT_SUBSTATION : BLUE_LEFT_SUBSTATION
				);
			}

			case RIGHT_SUBSTATION -> {
				goToAprilTagLogger.debug("Target is right substation");
				return new OTFToPoint(
						drivetrain,
						aprilTagPose.toPose2d(),
						isRed ? RED_RIGHT_SUBSTATION : BLUE_RIGHT_SUBSTATION
				);
			}
		}

		return null;
	}

	private static Command scoringGenerator(
			Drivetrain drivetrain,
			Elevator elevator,
			Intake intake,
			LBork lBork,
			LightBar lightBar,
			Pose3d aprilTagPose,
			int tagID
	) {
		generateScoringLog.debug("Function entered");
		var targetPosition = ScoringPosition.targetPosition;

		return switch(targetPosition) {
			case HIGH_LEFT_YANKEE, HIGH_PAPA, HIGH_RIGHT_YANKEE -> highScoringGenerator(
					drivetrain,
					elevator,
					intake,
					lBork,
					lightBar,
					aprilTagPose,
					tagID
			);
			case MIDDLE_LEFT_YANKEE, MIDDLE_PAPA, MIDDLE_RIGHT_YANKEE -> midScoringGenerator(
					drivetrain,
					elevator,
					intake,
					lBork,
					lightBar,
					aprilTagPose,
					tagID
			);
			case HYBRID_LEFT, HYBRID_MIDDLE, HYBRID_RIGHT -> hybridScoringGenerator(
					drivetrain,
					elevator,
					intake,
					lBork,
					lightBar,
					aprilTagPose,
					tagID
			);
		};
	}

	private static Command highScoringGenerator(
			Drivetrain drivetrain,
			Elevator elevator,
			Intake intake,
			LBork lBork,
			LightBar lightBar,
			Pose3d aprilTagPose,
			int tagID
	) {
		var sublist = new ArrayList<Command>();
		generateScoringLog.debug("Generating command for high scoring");
		sublist.add(
				new SequentialCommandGroup(
						goToAprilTagGenerator(
								drivetrain,
								switch (ScoringPosition.targetPosition) {
									case HIGH_LEFT_YANKEE -> Node.LEFT_YANKEE_NODE;
									case HIGH_PAPA -> Node.PAPA_NODE;
									case HIGH_RIGHT_YANKEE -> Node.RIGHT_YANKEE_NODE;
									default -> null;
								},
								aprilTagPose,
								tagID
						),
						new InstantCommand(() -> drivetrain.autoTankDriveVolts(2, 2)),
						tagID == 1
								? !ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_RIGHT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 6
								? !ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_RIGHT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 3
								? !ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_LEFT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 8
								? !ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_LEFT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						new InstantCommand(() -> drivetrain.autoTankDriveVolts(0, 0)),
						new SetSuperStructurePosition(
								elevator,
								intake,
								lBork,
								ELEVATOR_RAISED_POSITION,
								false,
								true
						),
						new ParallelRaceGroup(
								ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_PAPA)
										? new RunLBorkPapa(lBork, true)
										: new RunLBorkYankee(lBork, true),
								ScoringPosition.targetPosition.equals(ScoringPosition.HIGH_PAPA)
										? new WaitCommand(PAPA_OUTTAKE_TIME)
										: new WaitCommand(YANKEE_OUTTAKE_TIME)
						),
						new SetSuperStructurePosition(
								elevator,
								intake,
								lBork,
								ELEVATOR_IDLE_POSITION,
								false,
								false
						),
						new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_NO_PIECE))
				)
		);

		return new SequentialCommandGroup(sublist.toArray(new Command[0]));
	}

	private static Command midScoringGenerator(
			Drivetrain drivetrain,
			Elevator elevator,
			Intake intake,
			LBork lBork,
			LightBar lightBar,
			Pose3d aprilTagPose,
			int tagID
	) {
		var sublist = new ArrayList<Command>();
		generateScoringLog.debug("Generating command for mid scoring");
		sublist.add(
				new SequentialCommandGroup(
						goToAprilTagGenerator(
								drivetrain,
								switch (ScoringPosition.targetPosition) {
									case MIDDLE_LEFT_YANKEE -> Node.LEFT_YANKEE_NODE;
									case MIDDLE_PAPA -> Node.PAPA_NODE;
									case MIDDLE_RIGHT_YANKEE -> Node.RIGHT_YANKEE_NODE;
									default -> null;
								},
								aprilTagPose,
								tagID
						),
						new InstantCommand(() -> drivetrain.autoTankDriveVolts(2, 2)),
						tagID == 1
								? !ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_RIGHT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 6
								? !ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_RIGHT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 3
								? !ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_LEFT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						tagID == 8
								? !ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_LEFT_YANKEE)
										? new WaitCommand(0.5)
										: new InstantCommand(() -> {})
								: new InstantCommand(() -> {}),
						new InstantCommand(() -> drivetrain.autoTankDriveVolts(0, 0)),
						new SetSuperStructurePosition(
								elevator,
								intake,
								lBork,
								ELEVATOR_MID_POSITION,
								false,
								false
						),
						new ParallelRaceGroup(
								ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_PAPA)
										? new RunLBorkPapa(lBork, true)
										: new RunLBorkYankee(lBork, true),
								ScoringPosition.targetPosition.equals(ScoringPosition.MIDDLE_PAPA)
										? new WaitCommand(PAPA_OUTTAKE_TIME)
										: new WaitCommand(YANKEE_OUTTAKE_TIME)
						),
						new SetSuperStructurePosition(
								elevator,
								intake,
								lBork,
								ELEVATOR_IDLE_POSITION,
								false,
								false
						),
						new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_NO_PIECE))
				)
		);

		return new SequentialCommandGroup(sublist.toArray(new Command[0]));
	}

	private static Command hybridScoringGenerator(
			Drivetrain drivetrain,
			Elevator elevator,
			Intake intake,
			LBork lBork,
			LightBar lightBar,
			Pose3d aprilTagPose,
			int tagID
	) {
		var sublist = new ArrayList<Command>();
		generateScoringLog.debug("Generating command for hybrid scoring");
		sublist.add(
				new SequentialCommandGroup(
						goToAprilTagGenerator(
								drivetrain,
								switch (ScoringPosition.targetPosition) {
									case HYBRID_LEFT -> Node.LEFT_YANKEE_NODE;
									case HYBRID_MIDDLE -> Node.PAPA_NODE;
									case HYBRID_RIGHT -> Node.RIGHT_YANKEE_NODE;
									default -> null;
								},
								aprilTagPose,
								tagID
						),
						new SetSuperStructurePosition(
								elevator,
								intake,
								lBork,
								ScoringPosition.targetPosition.equals(ScoringPosition.HYBRID_MIDDLE)
										? ELEVATOR_PAPA_POSITION
										: ELEVATOR_IDLE_POSITION,
								ScoringPosition.targetPosition.equals(ScoringPosition.HYBRID_MIDDLE),
								false
						),
						new ParallelCommandGroup(
								ScoringPosition.targetPosition.equals(ScoringPosition.HYBRID_MIDDLE)
										? new RunLBorkPapa(lBork, true)
										: new RunLBorkYankee(lBork, true),
								ScoringPosition.targetPosition.equals(ScoringPosition.HYBRID_MIDDLE)
										? new RunIntake(intake, true)
										: new InstantCommand(() -> {})

						),
						new InstantCommand(() -> lightBar.set(LightBar.Profile.IDLE_NO_PIECE))
				)
		);

		return new SequentialCommandGroup(sublist.toArray(new Command[0]));
	}
}
