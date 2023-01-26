package org.frc1410.test.commands.groups;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;

import static org.frc1410.test.util.Trajectories.baseRamsete;
import static org.frc1410.test.util.Trajectories.config;

import java.util.List;

public class OTFToPoint extends SequentialCommandGroup {
    public OTFToPoint(Drivetrain drivetrain, Pose2d targetPose) {
        System.out.println("Current");
        System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getX()));
        System.out.println(Units.metersToInches(drivetrain.getPoseEstimation().getY()));
        System.out.println(drivetrain.getPoseEstimation().getRotation().getDegrees());
        System.out.println("Target");
        System.out.println(Units.metersToInches(targetPose.getX()));
        System.out.println(Units.metersToInches(targetPose.getY()));
        System.out.println(targetPose.getRotation().getDegrees());
        RamseteCommand command = baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(drivetrain.getPoseEstimation(), targetPose), config), drivetrain);

        addCommands(
                command,
                new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
        );
    }
}
