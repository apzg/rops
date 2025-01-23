package pl.poznan.demo.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

public class TaskRequest {

	public TaskRequest(String description, TaskStatus status, TaskPriority priority, LocalDateTime dueDate) {
		super();
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
	}

	@Schema(description = "Task description", example = "Task description")
	@NotBlank(message = "Task description is mandatory")
	@Size(min = 1, max = 255, message = "Task description is too long")
	private String description;
	@Schema(description = "Task status", example = "NEW")
	@NotNull(message = "Task status is mandatory")
	private TaskStatus status;
	@Schema(description = "Task priority", example = "LOW")
	@NotNull(message = "Task priority is mandatory")
	private TaskPriority priority;
	@Schema(description = "Task due date", example = "2026-01-23T16:00:00.028Z")
	private LocalDateTime dueDate;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
