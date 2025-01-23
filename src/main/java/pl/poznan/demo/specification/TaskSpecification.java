package pl.poznan.demo.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import pl.poznan.demo.entity.Project;
import pl.poznan.demo.entity.Task;
import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

public class TaskSpecification {

	public static final String COLUMN_DUE_DATE = "dueDate";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_STATUS = "status";
	public static final String JOIN_TABLE = "project";

	public static Specification<Task> filterBy(Long projectId, TaskStatus status, TaskPriority priority,
			LocalDateTime dueDate) {
		return Specification.where(hasProjectId(projectId)).and(hasValue(COLUMN_STATUS, status))
				.and(hasValue(COLUMN_PRIORITY, priority).and(hasValue(COLUMN_DUE_DATE, dueDate)));
	}

	private static Specification<Task> hasProjectId(Long projectId) {
		return (root, query, cb) -> {
			Join<Task, Project> taskProject = root.join(JOIN_TABLE);
			return cb.equal(taskProject.get("id"), projectId);
		};
	}

	private static Specification<Task> hasValue(String columnName, TaskStatus value) {
		return (root, query, cb) -> value == null ? cb.conjunction() : cb.equal(root.get(columnName), value);
	}

	private static Specification<Task> hasValue(String columnName, TaskPriority value) {
		return (root, query, cb) -> value == null ? cb.conjunction() : cb.equal(root.get(columnName), value);
	}

	private static Specification<Task> hasValue(String columnName, LocalDateTime value) {
		return (root, query, cb) -> value == null ? cb.conjunction() : cb.equal(root.get(columnName), value);
	}

}
