package com.rest.RestaurantApp.exceptions;

public class IncompatibleEmployeeTypeException extends RuntimeException {
	private String message;

	public IncompatibleEmployeeTypeException(String message) {
		this.message = message;
	}

	public IncompatibleEmployeeTypeException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
