package org.frc1410.framework.control.button;

import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.scheduler.task.Task;

public class ButtonBinding {

    private final XboxController controller;
    private final Button button;

    public ButtonBinding(XboxController controller, Button button) {
        this.controller = controller;
        this.button = button;
    }

    public boolean isPressed() {
        return controller.getRawButton(button.id);
    }
}