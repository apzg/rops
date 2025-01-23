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

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pl.poznan.demo.dto.BasePageRequest;
import pl.poznan.demo.dto.BasePageResponse;
import pl.poznan.demo.dto.ErrorResponse;
import pl.poznan.demo.dto.ProjectDto;
import pl.poznan.demo.dto.ProjectRequest;
import pl.poznan.demo.entity.Project;
import pl.poznan.demo.mapper.ProjectMapper;
import pl.poznan.demo.service.ProjectService;

@Tag(name = "Projects", description = "Crud operations for a projects")
@RestController
@RequestMapping("/projects")
public class ProjectController {

	public ProjectController(ProjectService service, ProjectMapper mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

	private ProjectService service;
	private ProjectMapper mapper;

	@Operation(summary = "Retrieve projects list", description = "List with pagination")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PostMapping("list")
	public BasePageResponse<ProjectDto> list(@Valid @RequestBody BasePageRequest request) {
		Page<Project> pageResult = service.list(PageRequest.of(request.getPage(), request.getSize()));
		return mapper.toPageResponse(pageResult);
	}

	@Operation(summary = "Retrieve project", description = "Get project details or null")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@GetMapping("/{projectId}")
	public ProjectDto get(@PathVariable Long projectId) {
		return mapper.toDto(service.get(projectId));
	}

	@Operation(summary = "Create new project", description = "Add and return the new project")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProjectDto create(@Valid @RequestBody ProjectRequest request) {
		return mapper.toDto(service.create(mapper.toEntity(request)));
	}

	@Operation(summary = "Update project", description = "Edit the editable project attributes and return the updated project")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@PutMapping("/{projectId}")
	public ProjectDto update(@PathVariable Long projectId, @Valid @RequestBody ProjectRequest request) {
		return mapper.toDto(service.update(projectId, mapper.toEntity(request)));
	}

	@Operation(summary = "Delete project", description = "Delete project and it's tasks")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "An unexpected error", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")) })
	@DeleteMapping("/{projectId}")
	public void delete(@PathVariable Long projectId) {
		service.delete(projectId);
	}

}
