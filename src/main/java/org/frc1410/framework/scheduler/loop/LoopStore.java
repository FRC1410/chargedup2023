package org.frc1410.framework.scheduler.loop;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;


public final class LoopStore {

    private final Long2ObjectOpenHashMap<Loop> loops = new Long2ObjectOpenHashMap<>();
    private final List<Loop> untracked = new ArrayList<>();

    private static final Loop MAIN = new Loop(-1L);

    public Loop main() {
        return MAIN;
    }

    public Loop ofPeriod(long period) {
        Loop loop = loops.get(period);
        if (loop == null) {
            loop = new Loop(period);
            loops.put(period, loop);
        }

        return loop;
    }

    public List<Loop> getUntrackedLoops() {
        List<Loop> copy = List.copyOf(untracked);
        untracked.clear();
        return copy;
    }
}