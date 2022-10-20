package org.frc1410.framework.control.button;

public abstract class ButtonObserver /*implements Observer*/ {

    private final Button button;

    public ButtonObserver(Button button) {
        this.button = button;
    }
}