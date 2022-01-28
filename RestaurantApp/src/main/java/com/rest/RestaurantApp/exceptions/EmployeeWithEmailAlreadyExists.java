package com.rest.RestaurantApp.exceptions;

public class EmployeeWithEmailAlreadyExists extends RuntimeException {
	private String message;

	public EmployeeWithEmailAlreadyExists(String message) {
		this.message = message;
	}

	public EmployeeWithEmailAlreadyExists() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
