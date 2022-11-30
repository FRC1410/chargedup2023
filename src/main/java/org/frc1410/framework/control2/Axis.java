package org.frc1410.framework.control2;

public class Axis {

    private static final double DEADZONE = 0.12;

    private final Controller controller;
    private final int id;

    public Axis(Controller controller, int id) {
        this.controller = controller;
        this.id = id;
    }

    public double getRaw() {
        return controller.backingController.getRawAxis(id);
    }

    public double get() {
        double raw = getRaw();
        double mag = Math.abs(raw);

        if (mag <= DEADZONE) {
            return 0;
        }

        return ((mag - DEADZONE) / (1 - DEADZONE)) * (raw / mag);
    }
}