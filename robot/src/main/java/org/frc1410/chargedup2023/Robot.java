package org.frc1410.chargedup2023;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.*;
import org.frc1410.chargedup2023.commands.DriveLooped;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.*;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.Barrier2ConeEngage;
import org.frc1410.chargedup2023.commands.groups.auto.outside.*;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
			.add("Barrier Score Collect", () -> new BarrierScoreCollect(drivetrain))
			.add("Barrier Score Collect Engage", () -> new BarrierScoreCollectEngage(drivetrain))
			.add("Barrier 2 Cone", () -> new Barrier2Cone(drivetrain))
			.add("Barrier 2 Cone Engage", () -> new Barrier2ConeEngage(drivetrain))
			.add("Barrier 2 Cone Collect Cube", () -> new Barrier2ConeCollectCube(drivetrain))
			.add("Barrier 2 Cone Cube", () -> new Barrier2ConeCube(drivetrain))

			.add("Outside Score Collect", () -> new OutsideScoreCollect(drivetrain))
			.add("Outside Score Collect Engage", () -> new OutsideScoreCollectEngage(drivetrain))
			.add("Outside 2 Cone", () -> new Outside2Cone(drivetrain))
			.add("Outside 2 Cone Engage", () -> new Outside2ConeEngage(drivetrain))
			.add("Outside 2 Cone Collect Cube", () -> new Outside2ConeCollectCube(drivetrain))
			.add("Outside 2 Cone Cube", () -> new Outside2ConeCube(drivetrain));


    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.zeroHeading();
        drivetrain.brakeMode();
        var autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleDefaultCommand(autoCommand, TaskPersistence.EPHEMERAL);
        System.out.println("Auto Done");
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.RIGHT_X_AXIS, driverController.LEFT_TRIGGER, driverController.RIGHT_TRIGGER), TaskPersistence.GAMEPLAY);
    }


    @Override
    public void testSequence() {
        drivetrain.coastMode();
    }

	@Override
	public void disabledSequence() {
		drivetrain.resetPoseEstimation(new Pose2d(0, 0, new Rotation2d(0)));
	}
}