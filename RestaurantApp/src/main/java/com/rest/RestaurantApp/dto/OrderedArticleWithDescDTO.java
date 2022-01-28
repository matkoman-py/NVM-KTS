package com.rest.RestaurantApp.dto;

public class OrderedArticleWithDescDTO {
	private Integer articleId;
	private String description;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OrderedArticleWithDescDTO(Integer articleId, String description) {
		super();
		this.articleId = articleId;
		this.description = description;
	}
	public OrderedArticleWithDescDTO() {
		super();
	}
	
}
