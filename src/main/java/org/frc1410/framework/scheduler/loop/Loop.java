package org.frc1410.framework.scheduler.loop;

import org.frc1410.framework.scheduler.task.BoundTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Loops hold bound tasks, and are ticked on their period. There is
 * also a default loop, that runs on the normal robot periodic tick
 * and is generally used for most tasks.
 *
 * <p>These loops are <i>not</i> the same as the loops found in 254's
 * code, for example, but they are similar. They do not get their own
 * thread, but they do handle execution of tasks.
 *
 * @see LoopStore#main()
 */
public class Loop {

    private final long period;
    private final List<BoundTask> tasks = new ArrayList<>();

    Loop(long period) {
        this.period = period;
    }

    public void add(BoundTask task) {
        tasks.add(task);
    }

    public void tick() {
        for (var task : tasks) {
            switch (task.lifecycleHandler.getState()) {
                
            }
        }
    }
}