package pl.poznan.demo.dto;

import java.time.LocalDateTime;

import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

public class TaskDto {

	public TaskDto(Long id, String description, TaskStatus status, TaskPriority priority, LocalDateTime dueDate) {
		super();
		this.id = id;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
	}

	private Long id;
	private String description;
	private TaskStatus status;
	private TaskPriority priority;
	private LocalDateTime dueDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
