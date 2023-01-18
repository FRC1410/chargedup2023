package org.frc1410.chargedup2023;

import edu.wpi.first.wpilibj.RobotBase;

public interface Main {
  static void main(String[] args) {
      // if this is committed that is a bug. revert
      RobotBase.startRobot(TestRobot::new);
  }
}