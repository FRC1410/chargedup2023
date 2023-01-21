package org.frc1410.test;

import edu.wpi.first.wpilibj.RobotBase;

import java.io.IOException;

public interface Main {
  static void main(String[] args) {
      RobotBase.startRobot(() -> {
          try {
              return new PostEstimTest();
          } catch (IOException ex) {
              throw new RuntimeException(ex);
          }
      });
  }
}