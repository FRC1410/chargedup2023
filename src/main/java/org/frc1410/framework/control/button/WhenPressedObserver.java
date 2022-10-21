package org.frc1410.framework.control.button;

import org.frc1410.framework.scheduler.task.BoundTask;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.Observer;

public class WhenPressedObserver extends Observer {

    private final Button button;

    public WhenPressedObserver(LifecycleHandler lifecycle, Button button) {
        super(lifecycle);
        this.button = button;
    }

    @Override
    public void tick() {
        if (button.isActive()) {
            lifecycle.requestExecution();
        }
    }
}