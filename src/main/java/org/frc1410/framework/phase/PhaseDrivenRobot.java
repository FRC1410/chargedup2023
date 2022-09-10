package org.frc1410.framework.phase;

import edu.wpi.first.wpilibj.TimedRobot;

public abstract class PhaseDrivenRobot extends TimedRobot {
	public final PhaseController phaseController = new PhaseController();

	@Override
	public final void robotPeriodic() {
		if (phaseController.isTransitioning()) {
			System.out.println("[Robot] Scheduler tick submitted during transition. Skipped.");
			return;
		}
	}

	// <editor-fold desc="> Phase hooks" defaultstate="collapsed">

	@Override
	public void robotInit() {
		System.out.println("!!!");
		// Signal that we're about to transition out of INIT as soon as the scheduler does a sweep
		phaseController.beginTransition();
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
		phaseController.transition(Phase.TElEOP);
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
	public void testPeriodic() {

	}

	// </editor-fold>
}
