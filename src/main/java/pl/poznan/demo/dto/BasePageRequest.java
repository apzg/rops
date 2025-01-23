package pl.poznan.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;

public class BasePageRequest {
	
	@Schema(description = "Number of a page", defaultValue = "0")
	// FIXME add cross validation between page and size so page offset couldn't be bigger then Integer.MAX_VALUE
	@Max(value = 1000)
	private int page = 0;
	@Schema(description = "Number of records on a page", defaultValue = "10")
	@Max(value = 100)
	private int size = 10;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
