package pl.poznan.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pl.poznan.demo.entity.Project;
import pl.poznan.demo.entity.Task;
import pl.poznan.demo.exception.ServiceException;
import pl.poznan.demo.repository.ProjectRepository;
import pl.poznan.demo.repository.TaskRepository;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

	public ProjectTaskServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository) {
		super();
		this.projectRepository = projectRepository;
		this.taskRepository = taskRepository;
	}

	private ProjectRepository projectRepository;
	private TaskRepository taskRepository;

	@Override
	public Page<Task> list(Specification<Task> specification, PageRequest page) {
		return taskRepository.findAll(specification, page);
	}

	@Override
	public Task get(Long taskId, Long projectId) {
		return taskRepository.findByIdAndProjectId(taskId, projectId).orElse(null);
	}

	@Override
	public Task create(Long projectId, Task entity) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ServiceException("projectId", "Project doesn't exist"));
		entity.setProject(project);
		return taskRepository.save(entity);
	}

	@Override
	public Task update(Long taskId, Long projectId, Task entity) {
		Task existingTask = taskRepository.findByIdAndProjectId(taskId, projectId)
				.orElseThrow(() -> new ServiceException("projectId", "Project or task doesn't exist"));
		existingTask.setDescription(entity.getDescription());
		existingTask.setStatus(entity.getStatus());
		existingTask.setPriority(entity.getPriority());
		existingTask.setDueDate(entity.getDueDate());
		return taskRepository.save(existingTask);
	}

	@Override
	@Transactional
	public void delete(Long taskId, Long projectId) {
		taskRepository.deleteByIdAndProjectId(taskId, projectId);
	}

}
