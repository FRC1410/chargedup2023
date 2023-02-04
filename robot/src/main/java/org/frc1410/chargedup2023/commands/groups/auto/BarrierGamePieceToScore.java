package org.frc1410.chargedup2023.commands.groups.auto;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frc1410.chargedup2023.subsystem.Drivetrain;
import org.frc1410.chargedup2023.util.Trajectories;

import static org.frc1410.chargedup2023.auto.POIs.BARRIER_GAME_PIECE_FORWARD;

public class BarrierGamePieceToScore extends SequentialCommandGroup {
	public BarrierGamePieceToScore(Drivetrain drivetrain) {
        drivetrain.resetPoseEstimation(BARRIER_GAME_PIECE_FORWARD);

        addCommands(
                Trajectories.BarrierGamePieceToScore(drivetrain),
                new InstantCommand(() -> {
					drivetrain.tankDriveVolts(0, 0);
					System.out.println("TRAJECTORY TIME = " + Trajectories.totalTime);
//					System.out.println("GYRO? = " + Trajectories.gyroProblem);
				}),
				new RunCommand(() -> {})
        );
	}
}