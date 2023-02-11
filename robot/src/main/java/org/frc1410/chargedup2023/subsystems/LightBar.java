package org.frc1410.chargedup2023.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.chargedup2023.util.IDs;

public class LightBar implements Subsystem {

	private final PWMSparkMax blinkin = new PWMSparkMax(IDs.BLINKIN_PORT);

	public void set(Profile profile) {
		blinkin.set(profile.id);
	}

	public enum Profile {
		CONE_PICKUP(0.69),
		CUBE_PICKUP(0.91),
		IDLE_STATE(0.75),
		;

		private final double id;

		// Find available IDs at https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
		Profile(double id) {
			this.id = id;
		}
	}
}