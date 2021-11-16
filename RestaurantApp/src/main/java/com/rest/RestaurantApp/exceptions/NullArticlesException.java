package com.rest.RestaurantApp.exceptions;

public class NullArticlesException extends RuntimeException {
	private String message;

	public NullArticlesException(String message) {
		this.message = message;
	}

	public NullArticlesException() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
