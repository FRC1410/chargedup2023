package org.frc1410.framework.scheduler.task;

import org.frc1410.framework.scheduler.subsystem.Subsystem;
import org.jetbrains.annotations.Nullable;

public abstract class Command implements Task {

    private Subsystem lock = null;

    protected final void dependsOn(Subsystem subsystem) {
        this.lock = subsystem;
    }

    @Override
    public final @Nullable Object getLockKey() {
        return lock;
    }
}