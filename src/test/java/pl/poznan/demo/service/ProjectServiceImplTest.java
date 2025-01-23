package pl.poznan.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import pl.poznan.demo.entity.Project;
import pl.poznan.demo.exception.ServiceException;
import pl.poznan.demo.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

	@Mock
	private ProjectRepository repositoryMock;

	private ProjectService service;

	@BeforeEach
	void setUp() {
		service = new ProjectServiceImpl(repositoryMock);
	}

	@Test
	void list() {
		when(repositoryMock.findAll(any(PageRequest.class)))
				.thenReturn(new PageImpl<Project>(new ArrayList<Project>()));

		Page<Project> response = service.list(PageRequest.of(0, 10));

		assertThat(response).isNotNull();
		assertThat(response.getContent()).isNotNull();
		verify(repositoryMock, times(1)).findAll(any(PageRequest.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void get_ProjectNotFound() {
		when(repositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

		Project response = service.get(1L);

		assertThat(response).isNull();
		verify(repositoryMock, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void get_ProjectFound() {
		when(repositoryMock.findById(any(Long.class))).thenReturn(Optional.of(new Project()));

		Project response = service.get(1L);

		assertThat(response).isNotNull();
		verify(repositoryMock, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void create() {
		when(repositoryMock.save(any(Project.class))).thenReturn(new Project());

		Project response = service.create(new Project());

		assertThat(response).isNotNull();
		verify(repositoryMock, times(1)).save(any(Project.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void update_ProjectNotFound() {
		when(repositoryMock.findById(any(Long.class)))
				.thenThrow(new ServiceException("projectId", "Project doesn't exist"));

		ServiceException ex = assertThrows(ServiceException.class, () -> service.update(1L, new Project()));

		assertThat(ex).isNotNull();
		assertThat(ex.getField()).isNotNull();
		assertThat(ex.getLocalizedMessage()).isNotNull();
		verify(repositoryMock, times(1)).findById(any(Long.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void update_ProjectFound() {
		when(repositoryMock.findById(any(Long.class))).thenReturn(Optional.of(new Project()));
		when(repositoryMock.save(any(Project.class))).thenReturn(new Project());

		Project response = service.update(1L, new Project());

		assertThat(response).isNotNull();
		verify(repositoryMock, times(1)).findById(any(Long.class));
		verify(repositoryMock, times(1)).save(any(Project.class));
		verifyNoMoreInteractions(repositoryMock);
	}

	@Test
	void delete() {
		service.delete(1L);

		verify(repositoryMock, times(1)).deleteById(any(Long.class));
		verifyNoMoreInteractions(repositoryMock);
	}

}
