package org.frc1410.framework.control;

import edu.wpi.first.wpilibj.XboxController;

/**
 * A generic interface that represents a type of input provided by
 * a driver through a control interface. The framework is designed
 * around the assumption that teams will be using the built-in WPI
 * {@link XboxController}, but it can be extended beyond that. All
 * inputs are keyed by an integer ID, which in the default case is
 * a button or axis ID.
 *
 * @see Axis
 * @see org.frc1410.framework.control.button.Button
 */
public interface ControlInput {

    int getInputId();
}