package com.profile.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Schema(description = "Response structure for errors")
public class ExceptionResponse {

	@Schema(description = "Error code", example = "GB001")
	private final String code;

	@Schema(description = "Error message", example = "PROFILE_NOT_FOUND")
	private final String message;

	@Schema(description = "List of details regarding the error", example = "[\"Profile not found\"]")
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