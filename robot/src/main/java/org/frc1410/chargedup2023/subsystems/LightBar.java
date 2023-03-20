package org.frc1410.chargedup2023.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frc1410.chargedup2023.util.IDs;

public class LightBar implements Subsystem {

	private final PWMSparkMax blinkin = new PWMSparkMax(IDs.BLINKIN_PORT);

	public void set(Profile profile) {
		blinkin.set(profile.id);
	}

	public double get() {
		return blinkin.get();
	}

	public enum Profile {
//		IDLE_NO_PIECE(0.53), // Color Waves, Color 1 and 2

		IDLE_NO_PIECE(-0.41), // Ocean palette
		IDLE_PIECE(-0.59), // fire medium
		APRIL_TAG(0.75), // Dark Green
		SCORING(-0.71), // Sinelon, Forest Palette
		SUBSTATION_NO_PIECE(0.91), // Violet
		SUBSTATION_PIECE(0.69), // Yellow
		AUTO(-0.47), // Twinkles, Forest Palette
		TEST(-0.37); // Color Waves, Forest Palette

		public final double id;

		// Find available IDs at https://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
		Profile(double id) {
			this.id = id;
		}
	}
}