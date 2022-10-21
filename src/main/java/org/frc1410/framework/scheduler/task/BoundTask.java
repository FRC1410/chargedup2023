package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.loop.Loop;

/**
 * Represents a task that is bound to a loop and being actively
 * ticked. This class acts as a manager over its child task and
 * is responsible for managing its lifecycle.
 *
 * @see Task
 * @see Loop
 */
public class BoundTask {

    public final LifecycleHandler lifecycleHandler = new LifecycleHandler(this);
    public final Task job;
    public final TaskPersistence persistence;
    public final Observer observer;
    public TaskState state = TaskState.FLAGGED_EXECUTION;


    public BoundTask(Task task, TaskPersistence persistence, Observer observer) {
        this.job = task;
        this.persistence = persistence;
        this.observer = observer;
    }
}