package com.asylum.apimanagement.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Error {

	private int status;
	private String message;
	private long timestamp;
	private List<String> errorDetails = new ArrayList<>();

	public Error(int status, String message, long timestamp) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public Error() {

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void addErrorDetails(String message) {

		errorDetails.add(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<String> errorDetails) {
		this.errorDetails = errorDetails;
	}

}
