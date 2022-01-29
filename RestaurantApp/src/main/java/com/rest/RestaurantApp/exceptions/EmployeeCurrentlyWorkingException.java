package com.rest.RestaurantApp.exceptions;


public class EmployeeCurrentlyWorkingException extends RuntimeException {
	private String message;

	public EmployeeCurrentlyWorkingException(String message) {
		this.message = message;
	}

	public EmployeeCurrentlyWorkingException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}


