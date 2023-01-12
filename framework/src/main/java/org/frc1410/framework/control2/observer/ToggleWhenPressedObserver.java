package org.frc1410.framework.control2.observer;

import org.frc1410.framework.control2.Button;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;

public class ToggleWhenPressedObserver implements Observer {

    private final Button button;
    private boolean listening = false;

    public ToggleWhenPressedObserver(Button button) {
        this.button = button;
    }

    @Override
    public void tick(@NotNull LifecycleHandler lifecycle) {
        if (listening && button.isActive()) {
            listening = false;

            if (lifecycle.state.isExecuting()) {
                lifecycle.requestSuspension();
            } else {
                lifecycle.requestExecution();
            }
        } else listening = true;
    }
}