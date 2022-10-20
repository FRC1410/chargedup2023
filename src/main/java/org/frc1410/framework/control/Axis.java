package org.frc1410.framework.control;

import edu.wpi.first.wpilibj.XboxController;
import org.intellij.lang.annotations.MagicConstant;

public class Axis implements ControlInput {

    public static final int LEFT_X = 0;
    public static final int LEFT_Y = 1;
    public static final int LEFT_TRIGGER = 2;
    
    public static final int RIGHT_TRIGGER = 3;
    public static final int RIGHT_X = 4;
    public static final int RIGHT_Y = 5;

    private final XboxController controller;
    private final int id;

    public Axis(XboxController controller, @MagicConstant(valuesFromClass = Axis.class) int id) {
        this.controller = controller;
        this.id = id;
    }

    @Override
    public XboxController getController() {
        return controller;
    }

    @Override
    public int getInputId() {
        return id;
    }
}