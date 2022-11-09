package org.frc1410.framework.scheduler.task;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandTask implements Task {

    private final Command command;

    public CommandTask(Command command) {
        this.command = command;
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
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}