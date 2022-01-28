package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.ArticleType;

public class OrderedArticleDTO {

	private int id;
	private int articleId;
	private ArticleStatus status;
	private int orderId;
	private boolean deleted;
	private String description;
	private String articleName;
	private String articleDescription;
	private double price;
	private String image;
	private ArticleType type;

	public String getArticleName() {
		return articleName;
	}


	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}


	public String getArticleDescription() {
		return articleDescription;
	}


	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public ArticleType getType() {
		return type;
	}


	public void setType(ArticleType type) {
		this.type = type;
	}


	public OrderedArticleDTO(int articleId, ArticleStatus status, int orderId, String description) {
		super();
		this.articleId = articleId;
		this.status = status;
		this.orderId = orderId;
		this.description = description;
	}
	
	
	public OrderedArticleDTO(int id, int articleId, ArticleStatus status, int orderId, boolean deleted,
			String description) {
		super();
		this.id = id;
		this.articleId = articleId;
		this.status = status;
		this.orderId = orderId;
		this.deleted = deleted;
		this.description = description;
	}


	public OrderedArticleDTO() {
		super();
	}
	public OrderedArticleDTO(OrderedArticle orderedArticle) {
		super();
		this.id = orderedArticle.getId();
		this.articleId = orderedArticle.getArticle().getId();
		this.articleName = orderedArticle.getArticle().getName();
		this.articleDescription = orderedArticle.getArticle().getDescription();
		if(orderedArticle.getArticle().getActivePrice() != null) {
			this.price = orderedArticle.getArticle().getActivePrice().getSellingPrice();
		}
		this.image = orderedArticle.getArticle().getImage();
		this.type = orderedArticle.getArticle().getType();
		this.status = orderedArticle.getStatus();
		this.orderId = orderedArticle.getOrder().getId();
		this.description = orderedArticle.getDescription();
		this.deleted = orderedArticle.isDeleted();
	}
	public OrderedArticleDTO(Integer articleId, String description) {
		super();
		this.articleId = articleId;
		this.description = description;
	}


	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
