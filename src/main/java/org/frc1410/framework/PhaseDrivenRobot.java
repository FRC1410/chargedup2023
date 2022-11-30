package org.frc1410.framework;

import edu.wpi.first.wpilibj.TimedRobot;
import org.frc1410.framework.phase.*;
import org.frc1410.framework.scheduler.loop.Loop;
import org.frc1410.framework.scheduler.subsystem.SubsystemPeriodicTask;
import org.frc1410.framework.scheduler.subsystem.SubsystemStore;
import org.frc1410.framework.scheduler.task.*;
import org.frc1410.framework.util.log.Logger;

public abstract class PhaseDrivenRobot extends TimedRobot {

    private static final Logger LOG = new Logger("Robot");

    protected final TaskScheduler scheduler = new TaskScheduler();
	protected final PhaseController phaseController = new PhaseController(scheduler);
    protected final SubsystemStore subsystems = new SubsystemStore();

    @Override
	public final void robotPeriodic() {
		if (phaseController.isTransitioning()) {
			LOG.warn("Scheduler tick submitted during transition. Skipped.");
			return;
		}

		// Tick the main loop. This loop just runs on the default robot period.
        scheduler.getLoopStore().main.tick();

		var loops = scheduler.getLoopStore().getUntrackedLoops();
		if (loops.isEmpty()) return;

		Loop loop;
		while ((loop = loops.pollFirst()) != null) {
			addPeriodic(loop::tick, loop.getPeriodSeconds());
		}
	}

	// <editor-fold desc="> Phase hooks" defaultstate="collapsed">

	@Override
	public final void robotInit() {
		LOG.info("Robot initialized.");
		// Signal that we're about to transition out of INIT as soon as the scheduler does a sweep
		phaseController.beginTransition();

        for (var subsystem : subsystems.getTickedSubsystems()) {
            scheduler.schedule(new SubsystemPeriodicTask(subsystem), TaskPersistence.DURABLE);
        }
	}

	public void disabledSequence() {

    }

    public void autonomousSequence() {

    }

    public void teleopSequence() {

    }

    public void testSequence() {

    }

	// Initialization methods.
	@Override
	public final void disabledInit() {
		phaseController.transition(Phase.DISABLED);
        disabledSequence();
	}

	@Override
	public final void autonomousInit() {
		phaseController.transition(Phase.AUTONOMOUS);
        autonomousSequence();
	}

	@Override
	public final void teleopInit() {
		phaseController.transition(Phase.TELEOP);
        teleopSequence();
	}

	@Override
	public final void testInit() {
		phaseController.transition(Phase.TEST);
        testSequence();
	}

	@Override
	public final void disabledExit() {
		phaseController.beginTransition();
	}

	@Override
	public final void autonomousExit() {
		phaseController.beginTransition();
	}

	@Override
	public final void teleopExit() {
		phaseController.beginTransition();
	}

	@Override
	public final void testExit() {
		phaseController.beginTransition();
	}

	// These methods have a default implementation that prints a warning. This adds overhead to the scheduler
	// that we want to avoid, so we just have blank methods.

	@Override
	public final void simulationPeriodic() {

	}

	@Override
	public final void disabledPeriodic() {

	}

	@Override
	public final void autonomousPeriodic() {

	}

	@Override
	public final void teleopPeriodic() {

	}

	@Override
	public final void testPeriodic() {

	}
	// </editor-fold>
}
