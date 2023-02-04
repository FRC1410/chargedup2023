package org.frc1410.chargedup2023.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.chargedup2023.util.IDs;

public class LightBar implements Subsystem {

	private final PWMSparkMax blinkin = new PWMSparkMax(IDs.BLINKIN_PORT);

	public void set(Profile profile) {
		blinkin.set(profile.id());
	}

	public interface Profile {

		/**
		 * Gets the floating-point ID for this profile.
		 *
		 * @return a double ID. Expects a profile from <a href="https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf">
		 *         the REV docs</a>.
		 */
		double id();
	}
}