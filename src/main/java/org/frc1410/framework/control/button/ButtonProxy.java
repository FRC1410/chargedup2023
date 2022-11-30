package org.frc1410.framework.control.button;

import org.frc1410.framework.scheduler.task.Task;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.framework.scheduler.task.observer.Observer;

public class ButtonProxy {

    private final Task task;
    private final Observer observer;

    public ButtonProxy(Task task, Observer observer) {
        this.task = task;
        this.observer = observer;
    }


    public void schedule(TaskScheduler scheduler, long period) {
        scheduler.schedule(task, TaskPersistence.GAMEPLAY, observer, LockPriority.HIGH, period);
    }

    public void schedule(TaskScheduler scheduler) {
        scheduler.schedule(task, TaskPersistence.GAMEPLAY, observer, LockPriority.HIGH);
    }
}