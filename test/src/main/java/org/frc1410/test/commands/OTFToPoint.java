package org.frc1410.test.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.test.subsystems.Drivetrain;

import static org.frc1410.test.util.Trajectories.baseRamsete;
import static org.frc1410.test.util.Trajectories.config;

import java.util.List;

public class OTFToPoint extends CommandBase {
    private final Drivetrain drivetrain;
    private final Pose2d targetPose;

    public OTFToPoint(Drivetrain drivetrain, Pose2d targetPose) {
        this.drivetrain = drivetrain;
        this.targetPose = targetPose;
    }

    @Override
    public void initialize() {
        baseRamsete(TrajectoryGenerator.generateTrajectory(List.of(drivetrain.getPoseEstimation(), targetPose), config), drivetrain);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
