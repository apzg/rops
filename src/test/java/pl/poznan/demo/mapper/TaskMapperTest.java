package pl.poznan.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.TaskDto;
import pl.poznan.demo.dto.TaskRequest;
import pl.poznan.demo.entity.Task;
import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

	@Test
	void toPageResponse_nullPage() {
		BasePageResponse<TaskDto> pageResponse = new TaskMapper().toPageResponse(null);

		assertThat(pageResponse).isNotNull();
		assertThat(pageResponse.getContent()).isNotNull();
		assertThat(pageResponse.getContent()).isEmpty();
		assertThat(pageResponse.getSize()).isEqualTo(0);
		assertThat(pageResponse.getTotalSize()).isEqualTo(0);
		assertThat(pageResponse.getPage()).isEqualTo(0);
		assertThat(pageResponse.getTotalPages()).isEqualTo(0);
	}

	@Test
	void toPageResponse() {
		BasePageResponse<TaskDto> pageResponse = new TaskMapper()
				.toPageResponse(new PageImpl<Task>(new ArrayList<Task>(), PageRequest.of(1, 1), 20));

		assertThat(pageResponse).isNotNull();
		assertThat(pageResponse.getContent()).isNotNull();
		assertThat(pageResponse.getContent()).isEmpty();
		assertThat(pageResponse.getSize()).isEqualTo(1);
		assertThat(pageResponse.getTotalSize()).isEqualTo(20);
		assertThat(pageResponse.getPage()).isEqualTo(1);
		assertThat(pageResponse.getTotalPages()).isEqualTo(20);
	}

	@Test
	void toDto_nullEntity() {
		TaskDto dto = new TaskMapper().toDto(null);

		assertThat(dto).isNull();
	}

	@Test
	void toDto() {
		Task entity = new Task("ttask description", TaskStatus.NEW, TaskPriority.LOW, LocalDateTime.now());
		TaskDto dto = new TaskMapper().toDto(entity);

		assertThat(dto).isNotNull();
		assertEquals(entity.getDescription(), dto.getDescription());
		assertEquals(entity.getStatus(), dto.getStatus());
		assertEquals(entity.getPriority(), dto.getPriority());
		assertEquals(entity.getDueDate(), dto.getDueDate());
	}

	@Test
	void toEntity_nullDto() {
		Task task = new TaskMapper().toEntity(null);

		assertThat(task).isNull();
	}

	@Test
	void toEntity() {
		TaskRequest request = new TaskRequest("task description", TaskStatus.NEW, TaskPriority.LOW,
				LocalDateTime.now());
		Task task = new TaskMapper().toEntity(request);

		assertThat(task).isNotNull();
		assertEquals(request.getDescription(), task.getDescription());
		assertEquals(request.getStatus(), task.getStatus());
		assertEquals(request.getPriority(), task.getPriority());
		assertEquals(request.getDueDate(), task.getDueDate());
	}

}
