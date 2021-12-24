package com.rest.RestaurantApp.dto;

public class DeleteMessageDTO {
	private String message;

	public DeleteMessageDTO(String message) {
		super();
		this.message = message;
	}

	public DeleteMessageDTO() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
