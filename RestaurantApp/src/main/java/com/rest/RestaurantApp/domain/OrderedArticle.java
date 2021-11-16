package com.rest.RestaurantApp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;

@Entity
@Where(clause = "deleted = false")
public class OrderedArticle extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ArticleStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee takenByEmployee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	public OrderedArticle(ArticleStatus status, Article article, Order order) {
		super();
		this.status = status;
		this.article = article;
		this.order = order;
	}
	
	public OrderedArticle(ArticleStatus status, Article article) {
		super();
		this.status = status;
		this.article = article;
	}

	public OrderedArticle() {
		super();
	}

	public ArticleStatus getStatus() {
		return status;
	}

	public void setStatus(ArticleStatus status) {
		this.status = status;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Employee getTakenByEmployee() {
		return takenByEmployee;
	}

	public void setTakenByEmployee(Employee takenByEmployee) {
		this.takenByEmployee = takenByEmployee;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
