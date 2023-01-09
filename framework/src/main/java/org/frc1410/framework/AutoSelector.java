package org.frc1410.framework;

import edu.wpi.first.wpilibj2.command.Command;
import org.frc1410.framework.scheduler.task.CommandTask;
import org.frc1410.framework.scheduler.task.TaskPersistence;
import org.frc1410.framework.scheduler.task.TaskScheduler;
import org.frc1410.framework.scheduler.task.lock.LockPriority;
import org.frc1410.framework.scheduler.task.observer.Observer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class responsible for the selection and dispatching of
 * autonomous routines. This class contains an ordered map of
 * profile names to their corresponding autonomous commands.
 */
public final class AutoSelector {

    private final List<@NotNull Profile> profiles = new ArrayList<>();

    @Contract(value = "_, _, _ -> this", mutates = "this")
    public AutoSelector add(@NotNull String profileName, @NotNull Command autoCommand, @Range(from = -1L, to = Long.MAX_VALUE) long period) {
        var profile = new Profile(profileName, autoCommand, profiles.size() + 1,  period);
        profiles.add(profile);

        return this;
    }

    @Contract(value = "_, _ -> this", mutates = "this")
    public AutoSelector add(@NotNull String profileName, @NotNull Command autoCommand) {
        return add(profileName, autoCommand, -1L);
    }

    /**
     * Gets a list of every registered auto profile.
     */
    public @NotNull List<@NotNull Profile> getProfiles() {
        return List.copyOf(profiles);
    }

    public void scheduleAuto(@NotNull TaskScheduler target, int profileId) {
        var profile = profiles.get(profileId);
        var command = profile.command();

        if (profile.period() != -1L) {
            target.schedule(new CommandTask(command), TaskPersistence.EPHEMERAL, Observer.DEFAULT, LockPriority.HIGH, profile.period);
        } else {
            target.schedule(new CommandTask(command), TaskPersistence.EPHEMERAL, Observer.DEFAULT, LockPriority.HIGH);
        }
    }

    private record Profile(@NotNull String name, @NotNull Command command, int id, long period) {
        Profile {
            Objects.requireNonNull(name, "Profile name cannot be null");
            Objects.requireNonNull(command, "Auto command cannot be null");
        }
    }
}