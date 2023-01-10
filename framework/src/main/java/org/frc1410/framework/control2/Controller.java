package org.frc1410.framework.control2;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.frc1410.framework.scheduler.task.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.Range;

import static edu.wpi.first.wpilibj.XboxController.Axis.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

public class Controller {

    final TaskScheduler scheduler;
    final XboxController backingController;

    public final Button A = new StandardButton(this, kA.value);
    public final Button B = new StandardButton(this, kB.value);
    public final Button X = new StandardButton(this, kX.value);
    public final Button Y = new StandardButton(this, kY.value);

    public final Button BACK = new StandardButton(this, kBack.value);
    public final Button START = new StandardButton(this, kStart.value);

    public final Button LEFT_BUMPER = new StandardButton(this, kLeftBumper.value);
    public final Button RIGHT_BUMPER = new StandardButton(this, kRightBumper.value);

    public final Button LEFT_STICK = new StandardButton(this, kLeftStick.value);
    public final Button RIGHT_STICK = new StandardButton(this, kRightStick.value);

    public final Axis LEFT_X_AXIS = new Axis(this, kLeftX.value);
    public final Axis RIGHT_X_AXIS = new Axis(this, kRightX.value);
    public final Axis LEFT_Y_AXIS = new Axis(this, kLeftY.value);
    public final Axis RIGHT_Y_AXIS = new Axis(this, kRightY.value);

    public final Axis LEFT_TRIGGER = new Axis(this, kLeftTrigger.value);
    public final Axis RIGHT_TRIGGER = new Axis(this, kRightTrigger.value);

    public Controller(TaskScheduler scheduler, int port) {
        this.scheduler = scheduler;
        this.backingController = new XboxController(port);
    }

    public void rumble(@Range(from = 0, to = 1) double strength) {
        backingController.setRumble(GenericHID.RumbleType.kBothRumble, strength);
    }

    public void rumbleLeft(double strength) {
        backingController.setRumble(GenericHID.RumbleType.kLeftRumble, strength);
    }

    public void rumbleRight(double strength) {
        backingController.setRumble(GenericHID.RumbleType.kRightRumble, strength);
    }

    private void timedRumble(@Range(from = 0, to = 1) double strength, long durationMillis, GenericHID.RumbleType rumbleType) {
        backingController.setRumble(rumbleType, strength);
        var resetCommand = new SequentialCommandGroup(
                new WaitCommand(durationMillis / 1000.0),
                new RunCommand(() -> backingController.setRumble(rumbleType, 0)));

        scheduler.schedule(new CommandTask(resetCommand), TaskPersistence.GAMEPLAY, Observer.DEFAULT, LockPriority.NULL);
    }

    public void rumble(@Range(from = 0, to = 1) double strength, long durationMillis) {
        timedRumble(strength, durationMillis, GenericHID.RumbleType.kBothRumble);
    }

    public void rumbleLeft(@Range(from = 0, to = 1) double strength, long durationMillis) {
        timedRumble(strength, durationMillis, GenericHID.RumbleType.kLeftRumble);
    }

    public void rumbleRight(@Range(from = 0, to = 1) double strength, long durationMillis) {
        timedRumble(strength, durationMillis, GenericHID.RumbleType.kRightRumble);
    }
}