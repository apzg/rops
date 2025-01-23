package pl.poznan.demo.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pl.poznan.demo.dto.ErrorResponse;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

	@Test
	void handleServiceException() {
		ServiceException ex = new ServiceException("fieldName", "error message");
		ResponseEntity<ErrorResponse> response = new GlobalExceptionHandler().handleServiceException(ex);

		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().getMessage()).isNotNull();
		assertThat(response.getBody().getFields()).isNotEmpty();
		assertThat(response.getBody().getFields()).size().isEqualTo(1);
		assertThat(response.getBody().getFields()).containsKeys(ex.getField());
		assertThat(response.getBody().getFields().get(ex.getField())).isEqualTo(ex.getMessage());
	}

}
