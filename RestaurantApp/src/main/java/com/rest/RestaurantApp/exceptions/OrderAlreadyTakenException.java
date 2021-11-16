package com.rest.RestaurantApp.exceptions;

public class OrderAlreadyTakenException extends RuntimeException {
	private String message;

	public OrderAlreadyTakenException(String message) {
		this.message = message;
	}

	public OrderAlreadyTakenException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
