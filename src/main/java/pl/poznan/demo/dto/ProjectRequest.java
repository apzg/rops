package pl.poznan.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

	public ProjectRequest(String name) {
		super();
		this.name = name;
	}

	@Schema(description = "Project name", example = "Some name")
	@NotBlank(message = "Project name is mandatory")
	@Size(min = 1, max = 255, message = "Project name is too long")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
