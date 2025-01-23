package pl.poznan.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import pl.poznan.demo.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
	
	Optional<Task> findByIdAndProjectId(Long id, Long projectId);

	void deleteByIdAndProjectId(Long id, Long projectId);
}
