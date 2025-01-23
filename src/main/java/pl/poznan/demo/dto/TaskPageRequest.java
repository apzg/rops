package pl.poznan.demo.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

public class TaskPageRequest extends BasePageRequest {

	@Schema(description = "List filter that will allow only tasks with matching status", example = "NEW")
	private TaskStatus status;
	@Schema(description = "List filter that will allow only tasks with matching priority", example = "LOW")
	private TaskPriority priority;
	@Schema(description = "List filter that will allow only tasks with matching due date", example = "2026-01-23T16:00:00.028Z")
	private LocalDateTime dueDate;

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

}
