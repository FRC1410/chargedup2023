package org.frc1410.framework.control.button;

import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;

public class WhenPressedObserver implements Observer {

    private final ButtonBinding target;
    private boolean lastState = false;

    public WhenPressedObserver(ButtonBinding target) {
        this.target = target;
    }

    @Override
    public void tick(@NotNull LifecycleHandler lifecycle) {
        boolean currentState = target.isPressed();

        if (!lastState && currentState) {
            lifecycle.requestExecution();
        }

        lastState = currentState;
    }
}

