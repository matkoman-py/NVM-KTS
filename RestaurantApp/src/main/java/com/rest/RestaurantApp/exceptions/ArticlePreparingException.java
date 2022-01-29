package com.rest.RestaurantApp.exceptions;

public class ArticlePreparingException extends RuntimeException {
	private String message;

	public ArticlePreparingException(String message) {
		this.message = message;
	}

	public ArticlePreparingException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
