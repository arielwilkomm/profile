package br.com.profile.user.exceptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private List<String> details;

	public ExceptionResponse(ErrorCodes errorCode, String details) {
		this.code = errorCode.name();
		this.message = errorCode.getMessage();
		this.details = Collections.singletonList(details);
	}

	public ExceptionResponse(ErrorCodes errorCode, List<String> details) {
		this.code = errorCode.name();
		this.message = errorCode.getMessage();
		this.details = details;
	}

	public ExceptionResponse(String code) {
		this.code = code;
	}

	public ExceptionResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ExceptionResponse(String code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = Collections.singletonList(details);
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return this.details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
