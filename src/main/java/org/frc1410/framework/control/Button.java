package org.frc1410.framework.control;

import edu.wpi.first.wpilibj.XboxController;
import org.intellij.lang.annotations.MagicConstant;

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

    @Override
    public XboxController getController() {
        return null;
    }

    @Override
    public int getInputId() {
        return 0;
    }
}