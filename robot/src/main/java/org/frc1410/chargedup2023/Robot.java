package org.frc1410.chargedup2023;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.*;
import org.frc1410.chargedup2023.commands.DriveLooped;
import org.frc1410.chargedup2023.commands.groups.OTFToPoint;
import org.frc1410.chargedup2023.commands.groups.auto.BlueBarrierPreload;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.*;
import org.frc1410.chargedup2023.commands.groups.auto.barrier.Barrier2ConeEngage;
import org.frc1410.chargedup2023.commands.groups.auto.outside.*;
import org.frc1410.chargedup2023.subsystems.Drivetrain;
import org.frc1410.chargedup2023.util.NetworkTables;
import org.frc1410.framework.AutoSelector;
import org.frc1410.framework.PhaseDrivenRobot;
import org.frc1410.framework.control.Controller;
import org.frc1410.framework.scheduler.task.LazyTask;
import org.frc1410.framework.scheduler.task.Task;
import org.frc1410.framework.scheduler.task.TaskPersistence;

import java.util.function.Supplier;

import static edu.wpi.first.math.util.Units.inchesToMeters;
import static org.frc1410.chargedup2023.util.Constants.*;

public final class Robot extends PhaseDrivenRobot {

    private final Controller driverController = new Controller(scheduler, DRIVER_CONTROLLER);
    private final Controller operatorController = new Controller(scheduler, OPERATOR_CONTROLLER);

    private final Drivetrain drivetrain = subsystems.track(new Drivetrain());

    private final NetworkTableInstance nt = NetworkTableInstance.getDefault();
    private final NetworkTable table = nt.getTable("Auto");

    private final AutoSelector autoSelector = new AutoSelector()
			.add("Go", () -> new BlueBarrierPreload(drivetrain));


    private final StringPublisher autoPublisher = NetworkTables.PublisherFactory(table, "Profile",
            autoSelector.getProfiles().get(0).name());
    private final StringSubscriber autoSubscriber = NetworkTables.SubscriberFactory(table, autoPublisher.getTopic());

    @Override
    public void autonomousSequence() {
        drivetrain.brakeMode();
        var autoProfile = autoSubscriber.get();
        var autoCommand = autoSelector.select(autoProfile);
        scheduler.scheduleAutoCommand(autoCommand);
        System.out.println("Auto Done");
    }

    @Override
    public void teleopSequence() {
        drivetrain.brakeMode();
        scheduler.scheduleDefaultCommand(new DriveLooped(drivetrain, driverController.LEFT_Y_AXIS, driverController.RIGHT_Y_AXIS, driverController.B), TaskPersistence.EPHEMERAL);
		driverController.B.whenPressed(
				LazyTask.fromCommand(
						() -> new OTFToPoint(
								drivetrain,
								new Translation2d(inchesToMeters(115), inchesToMeters(183.5)),
								new Pose2d(inchesToMeters(75), inchesToMeters(152.5), new Rotation2d(0))
						)
				),
				TaskPersistence.EPHEMERAL
		);
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