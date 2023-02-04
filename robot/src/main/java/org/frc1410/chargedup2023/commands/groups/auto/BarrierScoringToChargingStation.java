package org.frc1410.chargedup2023.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BARRIER_COMMUNITY_SCORE;

public class BarrierScoringToChargingStation extends SequentialCommandGroup {
    public BarrierScoringToChargingStation(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_SCORE);

        addCommands(
                Trajectories.BarrierScoreToChargingStation(drivetrain),
                new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0)),
                new RunCommand(() -> {})
        );
    }
}