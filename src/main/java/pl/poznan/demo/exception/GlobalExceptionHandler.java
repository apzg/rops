package pl.poznan.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import pl.poznan.demo.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
		if (logger.isDebugEnabled()) {
			logger.debug(ex.getLocalizedMessage(), ex);
		}

		ErrorResponse errorResponse = new ErrorResponse("Invalid data");
		errorResponse.getFields().put(ex.getField(), ex.getLocalizedMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		if (logger.isDebugEnabled()) {
			logger.debug(ex.getLocalizedMessage(), ex);
		}

		ErrorResponse errorResponse = new ErrorResponse("Invalid data");
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			// FIXME check the error class and return the message without implementation details
			errorResponse.getFields().put(((FieldError) error).getField(), error.getDefaultMessage());
		});

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(HttpMessageNotReadableException ex) {

		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException ifx = (InvalidFormatException) ex.getCause();
			if (ifx.getTargetType() != null) {
				if (logger.isDebugEnabled()) {
					logger.debug(ex.getLocalizedMessage(), ex);
				}

				ErrorResponse errorResponse = new ErrorResponse("Invalid data");
				errorResponse.getFields().put(ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(),
						ifx.getLocalizedMessage());
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			}
		}

		logger.error("Unhandled error occurred: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(new ErrorResponse("Unhandled error occurred"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
		logger.error("An error occurred: {}", ex.getMessage(), ex);

		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
