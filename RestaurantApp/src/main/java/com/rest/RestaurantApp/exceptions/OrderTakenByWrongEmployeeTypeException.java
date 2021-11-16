package com.rest.RestaurantApp.exceptions;

public class OrderTakenByWrongEmployeeTypeException extends RuntimeException  {


	private String message;

	public OrderTakenByWrongEmployeeTypeException(String message) {
		this.message = message;
	}

	public OrderTakenByWrongEmployeeTypeException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
