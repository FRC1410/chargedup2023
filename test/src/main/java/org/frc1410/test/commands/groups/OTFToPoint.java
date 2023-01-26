package org.frc1410.test.commands.groups;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;

import static org.frc1410.test.util.Trajectories.baseRamsete;
import static org.frc1410.test.util.Trajectories.slowConfig;

import java.util.List;

public class OTFToPoint extends SequentialCommandGroup {
    public OTFToPoint(Drivetrain drivetrain, Pose2d targetPose) {
        System.out.println("Target");
        System.out.println(Units.metersToInches(targetPose.getX()));
        System.out.println(Units.metersToInches(targetPose.getY()));
        System.out.println(targetPose.getRotation().getDegrees());
        RamseteCommand command = baseRamsete(
                TrajectoryGenerator.generateTrajectory(
                        List.of(drivetrain.getPoseEstimation(), targetPose),
                        slowConfig), drivetrain);

        addCommands(
                command
        );
    }
}
