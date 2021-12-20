package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderedArticleDTO {

	private int id;
	private int articleId;
	private ArticleStatus status;
	private int orderId;
	private boolean deleted;
	private String description;

	public OrderedArticleDTO(int articleId, ArticleStatus status, int orderId, String description) {
		super();
		this.articleId = articleId;
		this.status = status;
		this.orderId = orderId;
		this.description = description;
	}

	public OrderedArticleDTO(OrderedArticle orderedArticle) {
		super();
		this.id = orderedArticle.getId();
		this.articleId = orderedArticle.getArticle().getId();
		this.status = orderedArticle.getStatus();
		this.orderId = orderedArticle.getOrder().getId();
		this.description = orderedArticle.getDescription();
		this.deleted = orderedArticle.isDeleted();
	}
}
