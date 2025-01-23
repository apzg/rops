package pl.poznan.demo.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository repository;
	
	@Test
    void givenDataset_whenFindByIdAndProjectId_thenNoTaskFound() {		
        var task = repository.findByIdAndProjectId(999L, 999L);

        assertFalse(task.isPresent());
    }
	
	@Test
    void givenDataset_whenFindByIdAndProjectId_thenTaskFound() {		
        var task = repository.findByIdAndProjectId(1L, 1L);

        assertTrue(task.isPresent());
        assertThat(task.get().getDescription()).isNotBlank();
    }
	
	@Test
    void givenDataset_deleteByIdAndProjectId_thenTaskDeleted() {		
        repository.deleteByIdAndProjectId(1L, 1L);
        
        var task = repository.findByIdAndProjectId(1L, 1L);

        assertFalse(task.isPresent());
    }
	
}
