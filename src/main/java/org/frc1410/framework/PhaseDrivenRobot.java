package org.frc1410.framework;

import edu.wpi.first.wpilibj.TimedRobot;
import org.frc1410.framework.flow.PhaseSequencer;
import org.frc1410.framework.phase.*;
import org.frc1410.framework.scheduler.TaskScheduler;

public abstract class PhaseDrivenRobot extends TimedRobot {

	public final PhaseController phaseController = new PhaseController();
    private final TaskScheduler scheduler = new TaskScheduler();

	@Override
	public final void robotPeriodic() {
		if (phaseController.isTransitioning()) {
			System.out.println("[Robot] Scheduler tick submitted during transition. Skipped.");
			return;
		}

        scheduler.getLoopStore().main().tick();
	}

	// <editor-fold desc="> Phase hooks" defaultstate="collapsed">

	@Override
	public final void robotInit() {
		System.out.println("!!!");
		// Signal that we're about to transition out of INIT as soon as the scheduler does a sweep
		phaseController.beginTransition();
	}

	public void disabledSequence(PhaseSequencer seq) {
		/*
		new Button(driverController, Button.X)
		 */
	}

	public void autonomousSequence(PhaseSequencer seq) {
	}

	public void teleopSequence(PhaseSequencer seq) {

	}

	public void testSequence(PhaseSequencer seq) {

	}

	// Initialization methods.
	@Override
	public final void disabledInit() {
		phaseController.transition(Phase.DISABLED);
	}

	@Override
	public final void autonomousInit() {
		phaseController.transition(Phase.AUTONOMOUS);
	}

	@Override
	public final void teleopInit() {
		phaseController.transition(Phase.TELEOP);
	}

	@Override
	public final void testInit() {
		phaseController.transition(Phase.TEST);
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
