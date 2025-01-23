package pl.poznan.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import pl.poznan.demo.entity.Task;

public interface ProjectTaskService {

	Page<Task> list(Specification<Task> specification, PageRequest page);

	Task get(Long taskId, Long projectId);

	Task create(Long projectId, Task entity);

	Task update(Long taskId, Long projectId, Task entity);

	void delete(Long taskId, Long projectId);

}
