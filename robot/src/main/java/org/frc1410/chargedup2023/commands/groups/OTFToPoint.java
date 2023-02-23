package org.frc1410.chargedup2023.commands.groups;

import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import java.util.List;

import static org.frc1410.chargedup2023.util.Trajectories.*;

public class OTFToPoint extends SequentialCommandGroup {
    public OTFToPoint(Drivetrain drivetrain, Pose2d targetPose) {

        RamseteCommand command = baseRamsete(
                TrajectoryGenerator.generateTrajectory(List.of(drivetrain.getPoseEstimation(), targetPose),
                        slowConfig), Trajectories.tunedFeedforward, leftController, leftController, drivetrain);

        addRequirements(drivetrain);
        addCommands(command);
    }

    public OTFToPoint(Drivetrain drivetrain, Translation2d midPose, Pose2d targetPose) {
		var velocity = (drivetrain.getWheelSpeeds().leftMetersPerSecond + drivetrain.getWheelSpeeds().rightMetersPerSecond) / 2;
		reverseCentripOTFConfig.setStartVelocity(velocity);

		var trajectory = TrajectoryGenerator.generateTrajectory(
				drivetrain.getPoseEstimation(), List.of(midPose), targetPose, reverseCentripOTFConfig);

        RamseteCommand command = baseRamsete(trajectory, Trajectories.realisticFeedforward, leftController, rightController, drivetrain);
		addRequirements(drivetrain);
        addCommands(command.andThen(() -> drivetrain.tankDriveVolts(0, 0)));
    }
}

//		var trickyTrajectory = TrajectoryGenerator.generateTrajectory(controlVectorList, reverseConfigCentripAccel);
//		var velocity = (drivetrain.getWheelSpeeds().leftMetersPerSecond + drivetrain.getWheelSpeeds().rightMetersPerSecond) / 2;
//		var x = drivetrain.getPoseEstimation().getX();
//		var y = drivetrain.getPoseEstimation().getY();
//		var deg = drivetrain.getPoseEstimation().getRotation().getDegrees();
//		var dx = velocity * Math.cos(deg);
//		var dy = velocity * Math.sin(deg);
//
//		var controlVectorInitialX = new double[]{x, dx, 0};
//		var controlVectorInitialY = new double[]{y, dy, 0};
//		var controlVectorInitial = new Spline.ControlVector(controlVectorInitialX, controlVectorInitialY);
//
//		var controlVectorFinalX = new double[]{targetPose.getX(), 0, 0};
//		var controlVectorFinalY = new double[]{targetPose.getY(), 0, 0};
//		var controlVectorFinal = new Spline.ControlVector(controlVectorFinalX, controlVectorFinalY);
//
//		var controlVectorList = new TrajectoryGenerator.ControlVectorList(List.of(controlVectorInitial, controlVectorFinal));