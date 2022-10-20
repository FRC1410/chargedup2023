package org.frc1410.framework.scheduler.task;

@FunctionalInterface
public interface Observer {

    Observer DEFAULT = () -> false;

    boolean shouldCancel();
}