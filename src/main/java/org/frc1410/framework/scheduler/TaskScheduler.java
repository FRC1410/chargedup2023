package org.frc1410.framework.scheduler;

import org.frc1410.framework.scheduler.loop.LoopStore;

public class TaskScheduler {

    private final LoopStore loops = new LoopStore();

    // Registers tasks to the default loop.
    public void schedule(Task task, TaskPersistence persistence) {
        loops.getMain().add(task);
    }

    public void schedule(Task task, TaskPersistence persistence, long period) {

    }

    public LoopStore getLoops() {
        return loops;
    }
}