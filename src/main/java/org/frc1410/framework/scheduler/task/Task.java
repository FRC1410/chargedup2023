package org.frc1410.framework.scheduler.task;

public interface Task {

    void init();

    void execute();

    boolean isFinished();

    void end(boolean interrupted);

    default BoundTask bind(TaskPersistence persistence, Observer observer) {
        return new BoundTask(this, persistence, observer);
    }
}