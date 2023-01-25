package org.frc1410.test.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.test.subsystems.Drivetrain;
import org.frc1410.test.util.Trajectories;

import static org.frc1410.test.auto.POIs.BARRIER_COMMUNITY_START;

public class BarrierCommunityToChargingStation extends SequentialCommandGroup {
    public BarrierCommunityToChargingStation(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(BARRIER_COMMUNITY_START);

        addCommands(
                Trajectories.BarrierCommunityToChargingStation(drivetrain),
                new InstantCommand(() -> drivetrain.tankDriveVolts(0, 0))
        );
    }
}