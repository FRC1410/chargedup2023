package org.frc1410.framework.scheduler.task;

import edu.wpi.first.wpilibj2.command.Command;
import org.jetbrains.annotations.NotNull;

public record CommandTask(@NotNull Command command) implements Task {

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