package com.asylum.apimanagement.exceptions;

public class InvalidRequestException extends RuntimeException {

	public InvalidRequestException(String message, Throwable cause) {
		super(message, cause);

	}

	public InvalidRequestException(String message) {
		super(message);

	}

	public InvalidRequestException(Throwable cause) {
		super(cause);

	}
}
