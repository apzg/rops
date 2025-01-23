package pl.poznan.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.ProjectDto;
import pl.poznan.demo.dto.ProjectRequest;
import pl.poznan.demo.entity.Project;

@ExtendWith(MockitoExtension.class)
public class ProjectMapperTest {

	@Test
	void toPageResponse_nullPage() {
		BasePageResponse<ProjectDto> pageResponse = new ProjectMapper().toPageResponse(null);

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
		BasePageResponse<ProjectDto> pageResponse = new ProjectMapper()
				.toPageResponse(new PageImpl<Project>(new ArrayList<Project>(), PageRequest.of(1,1), 20));

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
		ProjectDto dto = new ProjectMapper().toDto(null);

		assertThat(dto).isNull();
	}

	@Test
	void toDto() {
		Project entity = new Project("project name");
		ProjectDto dto = new ProjectMapper().toDto(entity);

		assertThat(dto).isNotNull();
		assertEquals(entity.getName(), dto.getName());
	}

	@Test
	void toEntity_nullDto() {
		Project project = new ProjectMapper().toEntity(null);

		assertThat(project).isNull();
	}

	@Test
	void toEntity() {
		ProjectRequest request = new ProjectRequest("project name");
		Project project = new ProjectMapper().toEntity(request);

		assertThat(project).isNotNull();
		assertEquals(request.getName(), project.getName());
	}
}
