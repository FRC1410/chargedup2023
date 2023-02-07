package org.frc1410.framework.scheduler.task;


import edu.wpi.first.wpilibj2.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class LazyTask implements Task {

	private final @NotNull Supplier<Task> supplier;
	private Task wrapped;

	public LazyTask(@NotNull Supplier<Task> supplier) {
		this.supplier = supplier;
	}

	@Override
	public void init() {
		if (wrapped == null) {
			wrapped = supplier.get();
		}

		wrapped.init();
	}

	@Override
	public void execute() {
		wrapped.execute();
	}

	@Override
	public boolean isFinished() {
		return wrapped == null || wrapped.isFinished();
	}

	@Override
	public void end(boolean interrupted) {
		wrapped.end(interrupted);
		wrapped = null;
	}
}