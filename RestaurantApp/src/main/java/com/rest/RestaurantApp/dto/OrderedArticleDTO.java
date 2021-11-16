package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;

public class OrderedArticleDTO {

	private ArticleDTO article;
	private ArticleStatus status;
	private int orderId;
	public OrderedArticleDTO(ArticleDTO article, ArticleStatus status, int orderId) {
		super();
		this.article = article;
		this.status = status;
		this.orderId = orderId;
	}
	public OrderedArticleDTO() {
		super();
	}
	public OrderedArticleDTO(OrderedArticle orderedArticle) {
		super();
		this.article = new ArticleDTO(orderedArticle.getArticle());
		this.status = orderedArticle.getStatus();
		this.orderId = orderedArticle.getOrder().getId();
	}
	public ArticleDTO getArticle() {
		return article;
	}
	public void setArticle(ArticleDTO article) {
		this.article = article;
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
	
	
	
}
