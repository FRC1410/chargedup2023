package org.frc1410.framework.phase;

/**
 * A state machine responsible for controlling robot phase. This class is used in
 * the {@link PhaseDrivenRobot}, which handles the transition between phases when
 * mode enter/exit methods are called.
 */
public class PhaseController {
	private Phase phase = Phase.INIT;
	private Phase oldPhase = null;

	void beginTransition() {
		System.out.println("[PhaseController] Began transition out of " + phase);

		oldPhase = phase;
		phase = Phase.TRANSITION;
	}

	void transition(Phase phase) {
		if (oldPhase == null) {
			throw new IllegalStateException("Transition request was not submitted! This will lead to race conditions.");
		}

		System.out.printf("[PhaseController] Transition complete: %s -> %s\n", oldPhase, phase);
		this.oldPhase = null;
		this.phase = phase;
	}

	public Phase getPhase() {
		if (isTransitioning()) {
			throw new IllegalStateException("Cannot acquire phase during transition.");
		}

		return phase;
	}

	public boolean isTransitioning() {
		return phase == Phase.TRANSITION;
	}
}
