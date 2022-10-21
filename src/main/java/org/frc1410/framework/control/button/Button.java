package org.frc1410.framework.control.button;

import edu.wpi.first.wpilibj.XboxController;
import org.frc1410.framework.control.ControlInput;
import org.intellij.lang.annotations.MagicConstant;

/**
 * Represents an input from an {@link XboxController}. Button IDs are
 * provided as static members of this class.
 */
public class Button implements ControlInput {

    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;

    public static final int LEFT_BUMPER = 5;
    public static final int RIGHT_BUMPER = 6;

    public static final int BACK = 7;
    public static final int START = 8;
    
    public static final int LEFT_STICK = 9;
    public static final int RIGHT_STICK = 10;

    private final XboxController controller;
    private final int id;

    public Button(XboxController controller, @MagicConstant(valuesFromClass = Button.class) int id) {
        this.controller = controller;
        this.id = id;
    }

    public XboxController getController() {
        return controller;
    }

    public boolean isActive() {
        return controller.getRawButton(id);
    }

    @Override
    public int getInputId() {
        return id;
    }
}