package com.asylum.apimanagement.exceptions;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApplicationRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Error> handleException(ApplicationNotFoundException exc) {

		Error error = new Error();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Error> handleException(InvalidRequestException exc) {

		Error error = new Error();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
		return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
	}

	private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
		Error error = new Error();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Validation Error");
		error.setTimestamp(System.currentTimeMillis());
		for (org.springframework.validation.FieldError fieldError : fieldErrors) {
			error.addErrorDetails(fieldError.getDefaultMessage());
		}
		return error;
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Error> dataIntegrityViolationException(DataIntegrityViolationException ex) {
		Error error = new Error();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Application with same first name, last name, date of birth pair already exists");
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}

	
}
