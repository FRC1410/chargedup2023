package org.frc1410.framework.scheduler.task.impl;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.scheduler.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A wrapper around WPILib's {@link Command} class to
 * bind it to the custom scheduler.
 */
public final class CommandTask implements Task {

    private final Command command;

    public CommandTask(@NotNull Command command) {
        this.command = Objects.requireNonNull(command);
    }

    @Override
    public void init() {
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }
}