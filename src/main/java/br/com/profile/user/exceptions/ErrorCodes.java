package br.com.profile.user.exceptions;

public enum ErrorCodes {

	INTERNAL_SERVER_ERROR("Internal server error"), INVALID_REQUEST("400"), VALIDATION_FAILED("Validation failed"),
	CUSTOMER_NOT_FOUND("User not found");

	private final String message;

	ErrorCodes(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

}
