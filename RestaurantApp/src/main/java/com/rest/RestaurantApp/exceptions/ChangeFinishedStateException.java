package com.rest.RestaurantApp.exceptions;

public class ChangeFinishedStateException extends RuntimeException {
	private String message;

	public ChangeFinishedStateException(String message) {
		this.message = message;
	}

	public ChangeFinishedStateException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
