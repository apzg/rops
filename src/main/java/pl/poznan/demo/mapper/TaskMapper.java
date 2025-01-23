package pl.poznan.demo.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.TaskDto;
import pl.poznan.demo.dto.TaskRequest;
import pl.poznan.demo.entity.Task;

@Component
public class TaskMapper {

	public BasePageResponse<TaskDto> toPageResponse(Page<Task> page) {
		BasePageResponse<TaskDto> response = new BasePageResponse<TaskDto>();
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

	public TaskDto toDto(Task entity) {
		return entity == null ? null
				: new TaskDto(entity.getId(), entity.getDescription(), entity.getStatus(), entity.getPriority(),
						entity.getDueDate());
	}

	public Task toEntity(TaskRequest dto) {
		return dto == null ? null
				: new Task(dto.getDescription(), dto.getStatus(), dto.getPriority(), dto.getDueDate());
	}

}
