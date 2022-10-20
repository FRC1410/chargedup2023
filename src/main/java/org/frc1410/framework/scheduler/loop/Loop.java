package org.frc1410.framework.scheduler.loop;

import org.frc1410.framework.scheduler.Task;

import java.util.ArrayList;
import java.util.List;

public class Loop {

    private final long period;
    private final List<Task> tasks = new ArrayList<>();

    Loop(long period) {
        this.period = period;
    }

    public void add(Task task) {
        tasks.add(task);
    }
}