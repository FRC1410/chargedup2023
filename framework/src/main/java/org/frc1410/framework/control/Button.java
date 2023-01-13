package org.frc1410.framework.control;

import org.frc1410.framework.control.observer.WhenPressedObserver;
import org.frc1410.framework.control.observer.WhileHeldObserver;
import org.frc1410.framework.scheduler.task.Task;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;

public interface Button {

    TaskScheduler scheduler();

    boolean isActive();

    default void whenPressed(Task task, TaskPersistence persistence) {
        scheduler().schedule(task, persistence, new WhenPressedObserver(this), LockPriority.HIGH);
    }

    default void whileHeld(Task task, TaskPersistence persistence) {
        scheduler().schedule(task, persistence, new WhileHeldObserver(this), LockPriority.HIGH);
    }

    default void toggleWhenPressed(Task task, TaskPersistence persistence) {
        scheduler().schedule(task, persistence, new WhenPressedObserver(this), LockPriority.HIGH);
    }
}