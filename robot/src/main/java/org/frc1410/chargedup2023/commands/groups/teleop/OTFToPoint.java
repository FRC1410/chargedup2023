package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import java.util.List;

import static org.frc1410.chargedup2023.util.Constants.FIELD_WIDTH;
import static org.frc1410.chargedup2023.util.Trajectories.*;

public class OTFToPoint extends SequentialCommandGroup {
	public OTFToPoint(Drivetrain drivetrain, Pose2d tagPose, Pose2d offsetPose) {
		//<editor-fold desc="SOUT" defaultstate="collapsed">
		System.out.println("Drivetrain");
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
		System.out.println("Target");
		System.out.println(Units.metersToInches(offsetPose.getX()));
		System.out.println(Units.metersToInches(offsetPose.getY()));
		System.out.println(offsetPose.getRotation().getDegrees());
		//</editor-fold>
		tagPose = new Pose2d(tagPose.getX(), FIELD_WIDTH - tagPose.getY(), new Rotation2d((tagPose.getRotation().getRadians() + Math.PI) % (2*Math.PI)));
//		var velocity = (drivetrain.getWheelSpeeds().leftMetersPerSecond + drivetrain.getWheelSpeeds().rightMetersPerSecond) / 2;
		var velocity = 0;
		configCentripAccelOTF.setStartVelocity(velocity);

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
										FIELD_WIDTH - tagPose.transformBy(
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
						configCentripAccelOTF), realisticFeedforward, leftController, rightController, drivetrain);

		addRequirements(drivetrain);

		addCommands(
				command,
				new InstantCommand(() -> {
					System.out.println("Results");
					System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
					System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
					System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
				})
		);
	}

	public OTFToPoint(Drivetrain drivetrain, Pose2d tagPose, Translation2d midPose, Pose2d offsetPose) {
		//<editor-fold desc="SOUT" defaultstate="collapsed">
		System.out.println("Drivetrain");
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
		System.out.println("Waypoint");
		System.out.println(Units.metersToInches(midPose.getX()));
		System.out.println(Units.metersToInches(midPose.getY()));
		System.out.println("Target");
		System.out.println(Units.metersToInches(offsetPose.getX()));
		System.out.println(Units.metersToInches(offsetPose.getY()));
		System.out.println(offsetPose.getRotation().getDegrees());
		//</editor-fold>
		tagPose = new Pose2d(tagPose.getX(), FIELD_WIDTH - tagPose.getY(), new Rotation2d((tagPose.getRotation().getRadians() + Math.PI) % (2*Math.PI)));
//		var velocity = (drivetrain.getWheelSpeeds().leftMetersPerSecond + drivetrain.getWheelSpeeds().rightMetersPerSecond) / 2;
		var velocity = 0;
		configCentripAccelOTF.setStartVelocity(velocity);

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
								FIELD_WIDTH - tagPose.transformBy(
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
						configCentripAccelOTF), realisticFeedforward, leftController, rightController, drivetrain);

		addRequirements(drivetrain);

		addCommands(
				command,
				new InstantCommand(() -> {
					System.out.println("Results");
					System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
					System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
					System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
				})
		);
	}
}