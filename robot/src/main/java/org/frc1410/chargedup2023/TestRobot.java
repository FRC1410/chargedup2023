package org.frc1410.chargedup2023;

import edu.wpi.first.wpilibj2.command.RunCommand;
import org.frc1410.framework.PhaseDrivenRobot;

public class TestRobot extends PhaseDrivenRobot {

    private int autoId = 0;

    @Override
    protected void autonomousSequence() {
        var id = autoId++;
        scheduler.scheduleAutoCommand(new RunCommand(() -> System.out.println("Auto #" + id)));
    }

    @Override
    protected void teleopSequence() {
        System.out.println("Entered teleop");
    }
}
