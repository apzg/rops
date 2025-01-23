package pl.poznan.demo.dto;

public class ProjectDto {

	public ProjectDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
