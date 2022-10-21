package org.frc1410.framework.control.button;

import org.frc1410.framework.scheduler.task.LifecycleHandler;
import org.frc1410.framework.scheduler.task.Observer;

public class ToggleObserver extends Observer {

    private final Button button;
    private boolean wasActive = false;
    private boolean running = false;

    public ToggleObserver(LifecycleHandler lifecycle, Button button) {
        super(lifecycle);
        this.button = button;
    }

    @Override
    public void tick() {
        if (wasActive && !button.isActive()) {
            wasActive = false;
            return;
        }

        if (!wasActive && button.isActive()) {
            wasActive = true;

            running = !running;
            
            if (running) lifecycle.requestExecution();
            else lifecycle.requestInterruption();
        }
    }
}