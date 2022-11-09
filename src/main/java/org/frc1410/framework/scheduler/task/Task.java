package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.task.lock.TaskLock;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.Nullable;

public interface Task {

    void init();

    void execute();

    boolean isFinished();

    void end(boolean interrupted);

    @Nullable
    default TaskLock getLock() {
        return null;
    }

    default BoundTask bind(TaskPersistence persistence, Observer observer) {
        return new BoundTask(this, persistence, observer, getLock());
    }
}