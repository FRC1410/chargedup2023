package org.frc1410.framework.scheduler.subsystem;

import java.util.ArrayList;
import java.util.List;

public class SubsystemStore {

    private final List<Subsystem> subsystems = new ArrayList<>();
    private final List<TickedSubsystem> tickedSubsystems = new ArrayList<>();

    public void track(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    public void track() {}
}