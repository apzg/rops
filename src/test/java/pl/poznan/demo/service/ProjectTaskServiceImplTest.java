package pl.poznan.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import pl.poznan.demo.entity.Project;
import pl.poznan.demo.entity.Task;
import pl.poznan.demo.exception.ServiceException;
import pl.poznan.demo.repository.ProjectRepository;
import pl.poznan.demo.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectTaskServiceImplTest {

	@Mock
	private ProjectRepository projectRepositoryMock;
	@Mock
	private TaskRepository taskRepositoryMock;

	private ProjectTaskService service;

	@BeforeEach
	void setUp() {
		service = new ProjectTaskServiceImpl(projectRepositoryMock, taskRepositoryMock);
	}

	@Test
	void list() {
		when(taskRepositoryMock.findAll(ArgumentMatchers.<Specification<Task>>any(), any(PageRequest.class)))
				.thenReturn(new PageImpl<Task>(new ArrayList<Task>()));

		Page<Task> response = service.list(Specification.where(null), PageRequest.of(2, 3));

		assertThat(response).isNotNull();
		assertThat(response.getContent()).isNotNull();
		verify(taskRepositoryMock, times(1)).findAll(ArgumentMatchers.<Specification<Task>>any(),
				any(PageRequest.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}

	@Test
	void get_ProjectNotFound() {
		when(taskRepositoryMock.findByIdAndProjectId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

		Task response = service.get(1L, 1L);

		assertThat(response).isNull();
		verify(taskRepositoryMock, times(1)).findByIdAndProjectId(any(Long.class), any(Long.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}

	@Test
	void get_ProjectFound() {
		when(taskRepositoryMock.findByIdAndProjectId(any(Long.class), any(Long.class)))
				.thenReturn(Optional.of(new Task()));

		Task response = service.get(1L, 1L);

		assertThat(response).isNotNull();
		verify(taskRepositoryMock, times(1)).findByIdAndProjectId(any(Long.class), any(Long.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}

	@Test
	void create_ProjectNotFound() {
		when(projectRepositoryMock.findById(any(Long.class)))
				.thenThrow(new ServiceException("projectId", "Project doesn't exist"));

		ServiceException ex = assertThrows(ServiceException.class, () -> service.create(1L, new Task()));
		
		assertThat(ex).isNotNull();
		assertThat(ex.getField()).isNotNull();
		assertThat(ex.getLocalizedMessage()).isNotNull();
		verify(projectRepositoryMock, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(projectRepositoryMock);
		verifyNoInteractions(taskRepositoryMock);
	}

	@Test
	void create_ProjectFound() {
		when(projectRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(new Project()));
		when(taskRepositoryMock.save(any(Task.class))).thenReturn(new Task());

		Task response = service.create(1L, new Task());

		assertThat(response).isNotNull();
		verify(projectRepositoryMock, times(1)).findById(any(Long.class));
		verify(taskRepositoryMock, times(1)).save(any(Task.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoMoreInteractions(projectRepositoryMock);
	}

	@Test
	void update_ProjectNotFound() {
		when(taskRepositoryMock.findByIdAndProjectId(any(Long.class), any(Long.class)))
				.thenThrow(new ServiceException("projectId", "Project or task doesn't exist"));

		ServiceException ex = assertThrows(ServiceException.class, () -> service.update(1L, 1L, new Task()));

		assertThat(ex).isNotNull();
		assertThat(ex.getField()).isNotNull();
		assertThat(ex.getLocalizedMessage()).isNotNull();
		verify(taskRepositoryMock, times(1)).findByIdAndProjectId(any(Long.class), any(Long.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}

	@Test
	void update_ProjectFound() {
		when(taskRepositoryMock.findByIdAndProjectId(any(Long.class), any(Long.class)))
				.thenReturn(Optional.of(new Task()));
		when(taskRepositoryMock.save(any(Task.class))).thenReturn(new Task());

		Task response = service.update(1L, 1L, new Task());

		assertThat(response).isNotNull();
		verify(taskRepositoryMock, times(1)).findByIdAndProjectId(any(Long.class), any(Long.class));
		verify(taskRepositoryMock, times(1)).save(any(Task.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}

	@Test
	void delete() {
		service.delete(1L, 1L);

		verify(taskRepositoryMock, times(1)).deleteByIdAndProjectId(any(Long.class), any(Long.class));
		verifyNoMoreInteractions(taskRepositoryMock);
		verifyNoInteractions(projectRepositoryMock);
	}
}
