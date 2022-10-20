package org.frc1410.framework.scheduler.task;

public interface Observer {

    /**
     * The default observer. Never cancels, and unconditionally
     * requests execution.
     */
    Observer DEFAULT = new Observer() {

        @Override
        public boolean shouldCancel() {
            return false;
        }

        @Override
        public boolean shouldSchedule() {
            return true;
        }
    };

    boolean shouldCancel();

    boolean shouldSchedule();
}