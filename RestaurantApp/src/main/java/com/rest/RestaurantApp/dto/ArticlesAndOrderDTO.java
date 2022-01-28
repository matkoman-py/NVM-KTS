package com.rest.RestaurantApp.dto;

import java.util.List;

public class ArticlesAndOrderDTO {
	private List<OrderedArticleWithDescDTO> articles;
	private Integer orderId;
	
	public ArticlesAndOrderDTO(List<OrderedArticleWithDescDTO> articles, Integer orderId) {
		super();
		this.articles = articles;
		this.orderId = orderId;
	}
	public List<OrderedArticleWithDescDTO> getArticles() {
		return articles;
	}
	public void setArticles(List<OrderedArticleWithDescDTO> articles) {
		this.articles = articles;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public ArticlesAndOrderDTO() {
		super();
	}
}
