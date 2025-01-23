package pl.poznan.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.ErrorResponse;
import pl.poznan.demo.dto.TaskDto;
import pl.poznan.demo.dto.TaskPageRequest;
import pl.poznan.demo.dto.TaskRequest;
import pl.poznan.demo.entity.Task;
import pl.poznan.demo.mapper.TaskMapper;
import pl.poznan.demo.service.ProjectTaskService;
import pl.poznan.demo.specification.TaskSpecification;

@Tag(name = "Project tasks", description = "Crud operations for tasks assigned to the project. No task can exist without a project and moving task between projects is not allowed")
@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class ProjectTaskController {

	public ProjectTaskController(ProjectTaskService service, TaskMapper mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

	private ProjectTaskService service;
	private TaskMapper mapper;

	@Operation(summary = "Retrieve project tasks list", description = "List with pagination and basic filters")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PostMapping("list")
	BasePageResponse<TaskDto> list(@PathVariable Long projectId, @Valid @RequestBody TaskPageRequest request) {

		Page<Task> pageResult = service.list(
				TaskSpecification.filterBy(projectId, request.getStatus(), request.getPriority(), request.getDueDate()),
				PageRequest.of(request.getPage(), request.getSize()));
		return mapper.toPageResponse(pageResult);
	}

	@Operation(summary = "Retrieve task", description = "Get task details or null")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@GetMapping("/{taskId}")
	public TaskDto get(@PathVariable Long projectId, @PathVariable Long taskId) {
		return mapper.toDto(service.get(taskId, projectId));
	}

	@Operation(summary = "Create new task for project", description = "Add a new task for the project")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public TaskDto create(@PathVariable Long projectId, @Valid @RequestBody TaskRequest task) {
		return mapper.toDto(service.create(projectId, mapper.toEntity(task)));
	}

	@Operation(summary = "Update project task", description = "Edit the editable task attributes and return the updated task")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PutMapping("/{taskId}")
	public TaskDto update(@PathVariable Long projectId, @PathVariable Long taskId,
			@Valid @RequestBody TaskRequest task) {
		return mapper.toDto(service.update(taskId, projectId, mapper.toEntity(task)));
	}

	@Operation(summary = "Delete project task", description = "Delete the task from the project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@DeleteMapping("/{taskId}")
	public void delete(@PathVariable Long projectId, @PathVariable Long taskId) {
		service.delete(taskId, projectId);
	}

}
