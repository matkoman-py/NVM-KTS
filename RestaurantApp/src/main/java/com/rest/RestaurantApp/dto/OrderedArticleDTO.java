package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;

public class OrderedArticleDTO {

	private int id;
	private int articleId;
	private ArticleStatus status;
	private int orderId;
	private String description;
	public OrderedArticleDTO(int articleId, ArticleStatus status, int orderId, String description) {
		super();
		this.articleId = articleId;
		this.status = status;
		this.orderId = orderId;
		this.description = description;
	}
	public OrderedArticleDTO() {
		super();
	}
	public OrderedArticleDTO(OrderedArticle orderedArticle) {
		super();
		this.id = orderedArticle.getId();
		this.articleId = orderedArticle.getArticle().getId();
		this.status = orderedArticle.getStatus();
		this.orderId = orderedArticle.getOrder().getId();
		this.description = orderedArticle.getDescription();
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public ArticleStatus getStatus() {
		return status;
	}
	public void setStatus(ArticleStatus status) {
		this.status = status;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
}
