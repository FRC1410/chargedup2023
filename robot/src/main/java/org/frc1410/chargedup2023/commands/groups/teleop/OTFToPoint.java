package org.frc1410.chargedup2023.commands.groups.teleop;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;

import java.util.List;

import static org.frc1410.chargedup2023.util.Trajectories.*;

public class OTFToPoint extends SequentialCommandGroup {
	public OTFToPoint(Drivetrain drivetrain, Pose2d targetPose) {
		System.out.println("Drivetrain");
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
		System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
		System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
		System.out.println("Target");
		System.out.println(Units.metersToInches(targetPose.getX()));
		System.out.println(Units.metersToInches(targetPose.getY()));
		System.out.println(targetPose.getRotation().getDegrees());

		RamseteCommand command = baseRamsete(
				TrajectoryGenerator.generateTrajectory(
						List.of(drivetrain.getPoseEstimation(), targetPose),
						slowConfig), tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);

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
