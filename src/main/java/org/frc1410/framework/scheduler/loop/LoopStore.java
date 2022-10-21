package org.frc1410.framework.scheduler.loop;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.frc1410.framework.util.log.Logger;

import java.util.*;


public final class LoopStore {

    private static final Logger LOG = new Logger("LoopStore");

    private final Long2ObjectOpenHashMap<Loop> loops = new Long2ObjectOpenHashMap<>();
    private final Deque<Loop> untracked = new ArrayDeque<>();

    public final Loop main = new Loop(-1L);

    public Loop ofPeriod(long period) {
        Loop loop = loops.get(period);
        if (loop == null) {
            loop = new Loop(period);
            loops.put(period, loop);
            untracked.add(loop);

            LOG.info("Registered new loop #%08x running at period of %dms", loop.hashCode(), period);
        }

        return loop;
    }

    public Deque<Loop> getUntrackedLoops() {
        return untracked;
    }
}
