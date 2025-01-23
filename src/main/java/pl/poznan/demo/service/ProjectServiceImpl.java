package pl.poznan.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pl.poznan.demo.entity.Project;
import pl.poznan.demo.exception.ServiceException;
import pl.poznan.demo.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	public ProjectServiceImpl(ProjectRepository repository) {
		super();
		this.repository = repository;
	}

	private ProjectRepository repository;

	@Override
	public Page<Project> list(PageRequest page) {
		return repository.findAll(page);
	}

	@Override
	public Project get(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Project create(Project entity) {
		return repository.save(entity);
	}

	@Override
	public Project update(Long id, Project entity) {
		Project existingProject = repository.findById(id)
				.orElseThrow(() -> new ServiceException("projectId", "Project doesn't exist"));
		existingProject.setName(entity.getName());
		return repository.save(existingProject);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
