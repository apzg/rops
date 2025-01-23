package pl.poznan.demo.specification;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import pl.poznan.demo.entity.Task;
import pl.poznan.demo.entity.TaskPriority;
import pl.poznan.demo.entity.TaskStatus;

@ExtendWith(MockitoExtension.class)
public class TaskSpecificationTest {

	@Test
	void filterBy_noParameters() {
		Specification<Task> specification = TaskSpecification.filterBy(null, null, null, null);
		
		assertThat(specification).isNotNull();
	}
	
	@Test
	void filterBy_withParameters() {
		Specification<Task> specification = TaskSpecification.filterBy(1L, TaskStatus.NEW, TaskPriority.LOW, LocalDateTime.now());
		
		assertThat(specification).isNotNull();
	}
	
}
