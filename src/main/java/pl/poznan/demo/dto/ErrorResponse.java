package pl.poznan.demo.dto;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponse {

	public ErrorResponse(String message) {
		super();
		this.message = message;
		this.fields = new HashMap<String, String>();
	}

	@Schema(description = "Error description", example = "Invalid data")
	private String message;
	@Schema(description = "DTO object fields with corelated error messages", example = "{'projectId': 'Field required'}" )
	private Map<String, String> fields;

	public String getMessage() {
		return message;
	}

	public Map<String, String> getFields() {
		return fields;
	}

}
