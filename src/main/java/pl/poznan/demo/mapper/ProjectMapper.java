package pl.poznan.demo.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.ProjectDto;
import pl.poznan.demo.dto.ProjectRequest;
import pl.poznan.demo.entity.Project;

@Component
public class ProjectMapper {

	public BasePageResponse<ProjectDto> toPageResponse(Page<Project> page) {
		BasePageResponse<ProjectDto> response = new BasePageResponse<ProjectDto>();
		if (page != null) {
			response.setPage(page.getNumber());
			response.setSize(page.getSize());
			response.setTotalPages(page.getTotalPages());
			response.setTotalSize(page.getTotalElements());
			response.setContent(page.getContent().stream().map(this::toDto).collect(Collectors.toList()));
		} else {
			response.setContent(new ArrayList<>());
		}
		return response;
	}

	public ProjectDto toDto(Project entity) {
		return entity == null ? null : new ProjectDto(entity.getId(), entity.getName());
	}

	public Project toEntity(ProjectRequest dto) {
		return dto == null ? null : new Project(dto.getName());
	}

}
