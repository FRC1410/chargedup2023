package org.frc1410.framework.scheduler.task;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class DeferredTask implements Task {

	private final TaskScheduler scheduler;
	private final Supplier<Task> taskSupplier;
	private final TaskPersistence persistence;
	private final Observer observer;
	private final int lockPriority;
	private BoundTask task;

	public DeferredTask(TaskScheduler scheduler, Supplier<Task> taskSupplier, TaskPersistence persistence, Observer observer, int lockPriority) {
		this.scheduler = scheduler;
		this.taskSupplier = taskSupplier;
		this.persistence = persistence;
		this.observer = observer;
		this.lockPriority = lockPriority;
	}

	@Override
	public void init() {
		var job = taskSupplier.get();
		this.task = scheduler.schedule(job, TaskPersistence.EPHEMERAL, observer, lockPriority);
	}

	@Override
	public boolean isFinished() {
		return task.handle().state.isInactive();
	}

	@Override
	public void end(boolean interrupted) {
		task.handle().requestTermination();
	}
}