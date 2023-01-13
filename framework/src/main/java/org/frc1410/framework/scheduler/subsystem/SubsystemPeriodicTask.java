package org.frc1410.framework.scheduler.subsystem;

import edu.wpi.first.wpilibj.RobotBase;
import org.frc1410.framework.scheduler.task.Task;

public class SubsystemPeriodicTask implements Task {

    private final TickedSubsystem subsystem;

    public SubsystemPeriodicTask(TickedSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    @Override
    public void execute() {
        subsystem.periodic();

        if (RobotBase.isSimulation()) {
            subsystem.simulationPeriodic();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}