package org.frc1410.framework.control.button;

import org.frc1410.framework.scheduler.task.BoundTask;
import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.Observer;

public class WhileHeldObserver extends Observer {

    private final Button button;

    public WhileHeldObserver(LifecycleHandler lifecycle, Button button) {
        super(lifecycle);
        this.button = button;
    }

    @Override
    public void tick() {
        if (button.isActive()) {
            lifecycle.requestExecution();
        } else {
            lifecycle.requestInterruption();
        }
    }
}