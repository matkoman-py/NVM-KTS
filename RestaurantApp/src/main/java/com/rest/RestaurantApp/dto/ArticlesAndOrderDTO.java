package com.rest.RestaurantApp.dto;

import java.util.List;

public class ArticlesAndOrderDTO {
	private List<Integer> articleIds;
	private Integer orderId;
	public List<Integer> getArticleIds() {
		return articleIds;
	}
	public void setArticleIds(List<Integer> articleIds) {
		this.articleIds = articleIds;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public ArticlesAndOrderDTO(List<Integer> articleIds, Integer orderId) {
		super();
		this.articleIds = articleIds;
		this.orderId = orderId;
	}
	public ArticlesAndOrderDTO() {
		super();
	}
}
