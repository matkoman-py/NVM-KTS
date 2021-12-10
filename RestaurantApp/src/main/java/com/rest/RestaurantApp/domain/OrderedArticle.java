package com.rest.RestaurantApp.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;

@Entity
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
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
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	public OrderedArticle(ArticleStatus status, Article article, Order order, String description) {
		super();
		this.status = status;
		this.article = article;
		this.order = order;
		this.description = description;
	}
	
	public OrderedArticle(ArticleStatus status, Article article) {
		super();
		this.status = status;
		this.article = article;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderedArticle orderedArticle = (OrderedArticle) o;
        if (orderedArticle.getId() == null || this.getId() == null) {
        	if(orderedArticle.getStatus() == null || this.getStatus() == null) {
        		if(orderedArticle.getOrder() == null || this.getOrder() == null) {
        			if(orderedArticle.getArticle().getId() == this.getArticle().getId()) {
        				return true;
        			}
        			return false;
        		} else {
        			if((orderedArticle.getArticle().getId() == this.getArticle().getId()) && (orderedArticle.getOrder().getId() == this.getOrder().getId())) {
        				return true;
        			}
        			return false;
        		}
        	} else {
        		if(orderedArticle.getOrder() == null || this.getOrder() == null) {
        			if((orderedArticle.getArticle().getId() == this.getArticle().getId()) && (orderedArticle.getStatus().equals(this.getStatus()))) {
        				return true;
        			}
        			return false;
        		} else {
        			if((orderedArticle.getArticle().getId() == this.getArticle().getId()) && (orderedArticle.getOrder().getId() == this.getOrder().getId()) && (orderedArticle.getStatus().equals(this.getStatus()))) {
        				return true;
        			}
        			return false;
        		}
        	}
        	
            
        }
        return Objects.equals(this.getId(), orderedArticle.getId());
    }
	
	
}
