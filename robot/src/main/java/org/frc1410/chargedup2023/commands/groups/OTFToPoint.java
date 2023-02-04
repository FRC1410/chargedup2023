package org.frc1410.chargedup2023.commands.groups;

import edu.wpi.first.math.geometry.Pose2d;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
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
                        slowConfig), Trajectories.tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);

        addRequirements(drivetrain);
        addCommands(command);
    }

    public OTFToPoint(Drivetrain drivetrain, Translation2d midPose, Pose2d targetPose) {
        RamseteCommand command = baseRamsete(
                TrajectoryGenerator.generateTrajectory(drivetrain.getPoseEstimation(), List.of(midPose), targetPose,
                        slowConfig), Trajectories.tunedFeedforward, leftControllerSlow, rightControllerSlow, drivetrain);

        addRequirements(drivetrain);
        addCommands(command);
    }
}