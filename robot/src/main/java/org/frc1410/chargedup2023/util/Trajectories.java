package org.frc1410.chargedup2023.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.frc1410.chargedup2023.subsystem.Drivetrain;

import static org.frc1410.chargedup2023.util.Constants.*;
import static org.frc1410.chargedup2023.util.Tuning.*;

public class Trajectories {
    private final Drivetrain drivetrain;

    TrajectoryConfig config = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .setReversed(false);

    TrajectoryConfig reverseConfig = new TrajectoryConfig(MAX_SPEED, MAX_ACCEL)
        .setKinematics(KINEMATICS)
        .setReversed(true);
    public Trajectories(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public RamseteCommand generateRamsete(Trajectory trajectory) {
        return new RamseteCommand(
                trajectory,
                drivetrain::getPoseEstimation,
                new RamseteController(KB, KZ),
                new SimpleMotorFeedforward(KS, KV, KA),
                KINEMATICS,
                drivetrain::getWheelSpeeds,
                new PIDController(KP_VEL, 0, 0, 10.0 / 1000),
                new PIDController(KP_VEL, 0, 0, 10.0 / 1000),
                drivetrain::tankDriveVolts
        );
    }
}