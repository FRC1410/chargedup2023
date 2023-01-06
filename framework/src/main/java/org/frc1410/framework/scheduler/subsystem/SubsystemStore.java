package org.frc1410.framework.scheduler.subsystem;

import java.util.ArrayList;
import java.util.List;

public class SubsystemStore {

    private final List<Subsystem> subsystems = new ArrayList<>();
    private final List<TickedSubsystem> tickedSubsystems = new ArrayList<>();

    public <S extends Subsystem> S track(S subsystem) {
        if (subsystem instanceof TickedSubsystem) {
            tickedSubsystems.add((TickedSubsystem) subsystem);
        }
        subsystems.add(subsystem);

        return subsystem;
    }

    public List<TickedSubsystem> getTickedSubsystems() {
        return tickedSubsystems;
    }
}