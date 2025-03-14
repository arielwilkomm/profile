package com.profile.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExceptionResponse {

	private final String code;
	private final String message;
	private final List<String> details;

	public ExceptionResponse(String code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = Collections.singletonList(details);
	}

	public List<String> getDetails() {
		return new ArrayList<>(this.details);
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
