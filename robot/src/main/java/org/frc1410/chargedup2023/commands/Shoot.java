package org.frc1410.chargedup2023.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frc1410.chargedup2023.subsystem.Shooter;
import org.frc1410.chargedup2023.subsystem.VerticalStorage;

public class Shoot extends CommandBase {
    private final Shooter shooter;
    private final VerticalStorage verticalStorage;

    public Shoot(Shooter shooter, VerticalStorage verticalStorage) {
        this.shooter = shooter;
        this.verticalStorage = verticalStorage;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(shooter, verticalStorage);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.setSpeed(-0.2);
        verticalStorage.setSpeed(1);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setSpeed(0);
        verticalStorage.setSpeed(0);
    }
}
