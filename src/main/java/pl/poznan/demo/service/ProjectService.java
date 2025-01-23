package pl.poznan.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import pl.poznan.demo.entity.Project;

public interface ProjectService {

	Page<Project> list(PageRequest page);

	Project get(Long id);

	Project create(Project entity);

	Project update(Long id, Project entity);

	void delete(Long id);

}
