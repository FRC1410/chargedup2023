package org.frc1410.framework;

import edu.wpi.first.wpilibj2.command.Command;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A utility class for storing auto profiles and getting them from a profile
 * name. Auto profile commands are lazily loaded, and are only generated for
 * when they are needed. The list of profiles can also be accessed.
 */
public final class AutoSelector {

    private final List<@NotNull AutoProfile> profiles = new ArrayList<>();

    @Contract("_, _ -> this")
    public @NotNull AutoSelector add(@NotNull String name, @NotNull Supplier<@NotNull Command> commandSupplier) {
        var profile = new AutoProfile(name, commandSupplier, profiles.size());
        profiles.add(profile);
        return this;
    }

    public @NotNull Command select(@NotNull String profileName) {
        for (var profile : profiles) {
            if (profileName.equalsIgnoreCase(profile.name())) {
                return Objects.requireNonNull(profile.supplier().get(), "Generated auto command must not be null");
            }
        }

        throw new IllegalStateException("No such auto \"" + profileName + "\"");
    }

    public @NotNull List<@NotNull AutoProfile> getProfiles() {
        return profiles;
    }
}

record AutoProfile(@NotNull String name, @NotNull Supplier<@NotNull Command> supplier, int id) {
    AutoProfile {
        Objects.requireNonNull(name);
        Objects.requireNonNull(supplier);
    }
}