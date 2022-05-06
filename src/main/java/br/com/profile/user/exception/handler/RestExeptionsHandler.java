package br.com.profile.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.profile.user.exceptions.ExceptionResponse;
import br.com.profile.user.exceptions.UserException;

public class RestExeptionsHandler extends ResponseEntityExceptionHandler {
	
	private static final String NOT_FOUND = "NOT_FOUND";
	private static final String BAD_REQUEST = "BAD_REQUEST";
	private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("500", INTERNAL_SERVER_ERROR, ex.getMessage());

		request.getDescription(false);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

		ExceptionResponse exceptionResponse = new ExceptionResponse("404", NOT_FOUND, ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Object> handleValidateCreateCustomerException(UserException ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse("400", BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
	
}
