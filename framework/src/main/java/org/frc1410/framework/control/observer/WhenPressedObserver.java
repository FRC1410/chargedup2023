package org.frc1410.framework.control.observer;

import org.frc1410.framework.control.Button;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;

public class WhenPressedObserver implements Observer {

    private final Button button;
    private boolean wasActive;

    public WhenPressedObserver(Button button) {
        this.button = button;
    }

    @Override
    public void tick(@NotNull LifecycleHandler lifecycle) {
        if (button.isActive() && wasActive) {
            lifecycle.requestExecution();
            wasActive = true;
        }
        wasActive = !button.isActive();
    }
}