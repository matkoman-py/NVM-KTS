package com.rest.RestaurantApp.exceptions;

public class EmployeeWithPinAlreadyExists extends RuntimeException{
	private String message;

	public EmployeeWithPinAlreadyExists(String message) {
		this.message = message;
	}

	public EmployeeWithPinAlreadyExists() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
