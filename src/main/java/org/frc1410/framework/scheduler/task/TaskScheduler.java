package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.loop.Loop;
import org.frc1410.framework.scheduler.loop.LoopStore;
import org.frc1410.framework.scheduler.task.lock.LockHandler;
import org.frc1410.framework.scheduler.task.observer.Observer;

public class TaskScheduler {

    private final LoopStore loops = new LoopStore();
    public final LockHandler lockHandler = new LockHandler();

    // Registers tasks to the default loop.
    public void schedule(Task task, TaskPersistence persistence) {
        schedule(task.bind(persistence, null), loops.main);
    }

    public void schedule(Task task, TaskPersistence persistence, long period) {
        schedule(task.bind(persistence, null), loops.ofPeriod(period));
    }

    // TODO: These will probably be abstracted over. We don't really want to expose observers directly.
    public void schedule(Task task, TaskPersistence persistence, Observer observer) {
        schedule(task.bind(persistence, observer), loops.main);
    }

    public void schedule(Task task, TaskPersistence persistence, long period, Observer observer) {
        schedule(task.bind(persistence, observer), loops.ofPeriod(period));
    }

    private void schedule(BoundTask task, Loop loop) {
        loop.add(task);
    }

    public LoopStore getLoopStore() {
        return loops;
    }
}
