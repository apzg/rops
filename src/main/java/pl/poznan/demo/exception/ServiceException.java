package pl.poznan.demo.exception;

public class ServiceException extends RuntimeException {

	public ServiceException(String field, String message) {
		super(message);
		this.field = field;
	}

	private String field;

	public String getField() {
		return field;
	}

}
