package org.frc1410.framework.control;

import org.frc1410.framework.scheduler.task.TaskScheduler;

public record AxisButton(TaskScheduler scheduler, Axis axis) implements Button {

	@Override
	public boolean isActive() {
		return axis.get() != 0;
	}
}