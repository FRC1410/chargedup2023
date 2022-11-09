package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.loop.Loop;
import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a task that is bound to a loop and being actively
 * ticked. This class acts as a manager over its child task and
 * is responsible for managing its lifecycle.
 *
 * @see Task
 * @see Loop
 */
public class BoundTask {

    public final LifecycleHandler lifecycle = new LifecycleHandler(this);
    public final Task job;
    public final TaskPersistence persistence;
    public final Observer observer;
    public final TaskLock lock;

    public BoundTask(Task task, TaskPersistence persistence, Observer observer, @Nullable TaskLock lock) {
        this.job = task;
        this.persistence = persistence;
        this.observer = observer;
        this.lock = lock;
    }
}