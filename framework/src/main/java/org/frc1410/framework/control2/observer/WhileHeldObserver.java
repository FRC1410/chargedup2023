package org.frc1410.framework.control2.observer;

import org.frc1410.framework.control2.Button;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.NotNull;

public class WhileHeldObserver implements Observer {

    private final Button button;

    public WhileHeldObserver(Button button) {
        this.button = button;
    }

    @Override
    public void tick(@NotNull LifecycleHandler lifecycle) {
        if (button.isActive()) {
            lifecycle.requestExecution();
        } else {
            lifecycle.requestInterruption();
        }
    }
}