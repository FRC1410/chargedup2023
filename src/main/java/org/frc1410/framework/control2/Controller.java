package org.frc1410.framework.control2;

import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.scheduler.task.TaskScheduler;

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
}