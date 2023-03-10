package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.framework.util.log.Logger;

import java.util.List;

import static org.frc1410.chargedup2023.util.Trajectories.*;

public class OTFToPoint extends SequentialCommandGroup {
	private static final Logger log = new Logger("OTFToPoint");

	public OTFToPoint(Drivetrain drivetrain, Pose2d tagPose, Pose2d offsetPose) {
		//<editor-fold desc="SOUT" defaultstate="collapsed">
		log.debug("Drivetrain X: " + Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		log.debug("Drivetrain Y: " + Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		log.debug("Drivetrain Rotation: " + Units.metersToInches(drivetrain.getPoseEstimation().getRotation().getDegrees()));

		log.debug("Target X: " + Units.metersToInches(
				tagPose.transformBy(
					new Transform2d(
							offsetPose.getTranslation(),
							Rotation2d.fromDegrees(180)
				)).getX()));
		log.debug("Target Y: " + Units.metersToInches(
				tagPose.transformBy(
					new Transform2d(
							offsetPose.getTranslation(),
							Rotation2d.fromDegrees(180)
				)).getY()));
		log.debug("Target Rotation: " + tagPose.transformBy(
					new Transform2d(
							offsetPose.getTranslation(),
							Rotation2d.fromDegrees(180)
					)
				).getRotation().getDegrees());

		log.debug("Starting Velocity: " + drivetrain.getVelocity());
		//</editor-fold>

		RamseteCommand command = baseRamsete(
				TrajectoryGenerator.generateTrajectory(
						List.of(drivetrain.getPoseEstimation(),
								new Pose2d(
										tagPose.transformBy(
												new Transform2d(
														offsetPose.getTranslation(),
														Rotation2d.fromDegrees(180)
												)
										).getX(),
										tagPose.transformBy(
												new Transform2d(
														offsetPose.getTranslation(),
														Rotation2d.fromDegrees(180)
												)
										).getY(),
										tagPose.transformBy(
												new Transform2d(
														offsetPose.getTranslation(),
														Rotation2d.fromDegrees(180)
												)
										).getRotation()
								)
						),
						configCentripAccelOTF), feedForwardTeleop, leftControllerTeleop, rightControllerTeleop, drivetrain);

		addRequirements(drivetrain);

		addCommands(
				command,
				new InstantCommand(() -> {
					drivetrain.autoTankDriveVolts(0, 0);
					log.debug("Result X: " + drivetrain.getPoseEstimation().getX());
					log.debug("Result Y: " + drivetrain.getPoseEstimation().getY());
					log.debug("Result Rotation: " + drivetrain.getPoseEstimation().getRotation().getDegrees());
				})
		);
	}

	public OTFToPoint(Drivetrain drivetrain, Pose2d tagPose, Pose2d offsetPose, boolean isCoopertitionGrid) {
		//<editor-fold desc="SOUT" defaultstate="collapsed">
		log.debug("Drivetrain X: " + Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		log.debug("Drivetrain Y: " + Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		log.debug("Drivetrain Rotation: " + Units.metersToInches(drivetrain.getPoseEstimation().getRotation().getDegrees()));

		log.debug("Target X: " + Units.metersToInches(
				tagPose.transformBy(
						new Transform2d(
								offsetPose.getTranslation(),
								Rotation2d.fromDegrees(180)
						)).getX()));
		log.debug("Target Y: " + Units.metersToInches(
				tagPose.transformBy(
						new Transform2d(
								offsetPose.getTranslation(),
								Rotation2d.fromDegrees(180)
						)).getY()));
		log.debug("Target Rotation: " + tagPose.transformBy(
				new Transform2d(
						offsetPose.getTranslation(),
						Rotation2d.fromDegrees(180)
				)
		).getRotation().getDegrees());

		log.debug("Starting Velocity: " + drivetrain.getVelocity());
		//</editor-fold>

		Trajectory trajectory;

		if (isCoopertitionGrid) {
			trajectory = TrajectoryGenerator.generateTrajectory(
					List.of(drivetrain.getPoseEstimation(),
							new Pose2d(
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getX(),
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getY(),
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getRotation()
							)
					),
					coopertitionConfigOTF);
		} else {
			trajectory = TrajectoryGenerator.generateTrajectory(
					List.of(drivetrain.getPoseEstimation(),
							new Pose2d(
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getX(),
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getY(),
									tagPose.transformBy(
											new Transform2d(
													offsetPose.getTranslation(),
													Rotation2d.fromDegrees(180)
											)
									).getRotation()
							)
					),
					configCentripAccelOTF);
		}

		RamseteCommand command = baseRamsete(trajectory, feedForwardTeleop,
				leftControllerTeleop, rightControllerTeleop, drivetrain);

		addRequirements(drivetrain);

		addCommands(
				command,
				new InstantCommand(() -> {
					drivetrain.autoTankDriveVolts(0, 0);
					log.debug("Result X: " + drivetrain.getPoseEstimation().getX());
					log.debug("Result Y: " + drivetrain.getPoseEstimation().getY());
					log.debug("Result Rotation: " + drivetrain.getPoseEstimation().getRotation().getDegrees());
				})
		);
	}

	public OTFToPoint(Drivetrain drivetrain, Pose2d tagPose, Translation2d midPose, Pose2d offsetPose) {
		//<editor-fold desc="SOUT" defaultstate="collapsed">
		log.debug("Drivetrain X: " + Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		log.debug("Drivetrain Y: " + Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		log.debug("Drivetrain Rotation: " + Units.metersToInches(drivetrain.getPoseEstimation().getRotation().getDegrees()));

		log.debug("Waypoint X: " + midPose.getX());
		log.debug("Waypoint Y: " + midPose.getY());

		log.debug("Target X: " + Units.metersToInches(
				tagPose.transformBy(
						new Transform2d(
								offsetPose.getTranslation(),
								Rotation2d.fromDegrees(180)
						)).getX()));
		log.debug("Target Y: " + Units.metersToInches(
				tagPose.transformBy(
						new Transform2d(
								offsetPose.getTranslation(),
								Rotation2d.fromDegrees(180)
						)).getY()));
		log.debug("Target Rotation: " + tagPose.transformBy(
				new Transform2d(
						offsetPose.getTranslation(),
						Rotation2d.fromDegrees(180)
				)
		).getRotation().getDegrees());

		log.debug("Starting Velocity: " + drivetrain.getVelocity());
		//</editor-fold>

		RamseteCommand command = baseRamsete(
				TrajectoryGenerator.generateTrajectory(
						drivetrain.getPoseEstimation(),
						List.of(midPose),
						new Pose2d(
								tagPose.transformBy(
										new Transform2d(
												offsetPose.getTranslation(),
												Rotation2d.fromDegrees(180)
										)
								).getX(),
								tagPose.transformBy(
										new Transform2d(
												offsetPose.getTranslation(),
												Rotation2d.fromDegrees(180)
										)
								).getY(),
								tagPose.transformBy(
										new Transform2d(
												offsetPose.getTranslation(),
												Rotation2d.fromDegrees(180)
										)
								).getRotation()
						),
						configCentripAccelOTF), feedForwardTeleop, leftControllerTeleop, rightControllerTeleop, drivetrain);

		addRequirements(drivetrain);

		addCommands(
				command,
				new InstantCommand(() -> {
					drivetrain.autoTankDriveVolts(0, 0);
					log.debug("Result X: " + drivetrain.getPoseEstimation().getX());
					log.debug("Result Y: " + drivetrain.getPoseEstimation().getY());
					log.debug("Result Rotation: " + drivetrain.getPoseEstimation().getRotation().getDegrees());
				})
		);
	}
}