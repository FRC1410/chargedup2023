package org.frc1410.framework.control;

import edu.wpi.first.wpilibj.XboxController;

public interface ControlInput {

    XboxController getController();

    int getInputId();
}