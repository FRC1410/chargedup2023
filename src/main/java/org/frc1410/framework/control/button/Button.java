package org.frc1410.framework.control.button;

import edu.wpi.first.wpilibj.XboxController;

public class Button {

    public static final Button LEFT_BUMPER = new Button(5);
    public static final Button RIGHT_BUMPER = new Button(6);
    public static final Button LEFT_STICK = new Button(7);
    public static final Button RIGHT_STICK = new Button(10);

    public static final Button A = new Button(1);
    public static final Button B = new Button(2);
    public static final Button X = new Button(3);
    public static final Button Y = new Button(4);
    public static final Button BACK = new Button(7);
    public static final Button START = new Button(8);

    final int id;

    private Button(int id) {
        this.id = id;
    }

    public ButtonBinding bind(XboxController controller) {
        return new ButtonBinding(controller, this);
    }
}