package com.rest.RestaurantApp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.PriceStatus;

@Entity
@Table(name = "prices")
@Where(clause = "deleted = false")
public class PriceInfo extends BaseEntity {
	
	@Column(nullable = false)
	private Date fromDate;
	
	@Column
	private Date toDate;
	
	@Column(nullable = false)
	private double makingPrice;
	
	@Column(nullable = false)
	private double sellingPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PriceStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	public PriceInfo(Date fromDate, double makingPrice, double sellingPrice, Article article) {
		super();
		this.fromDate = fromDate;
		this.makingPrice = makingPrice;
		this.sellingPrice = sellingPrice;
		this.status = PriceStatus.ACTIVE;
		this.article = article;
	}

	public PriceInfo() {
		super();
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public double getMakingPrice() {
		return makingPrice;
	}

	public void setMakingPrice(double makingPrice) {
		this.makingPrice = makingPrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public PriceStatus getStatus() {
		return status;
	}

	public void setStatus(PriceStatus status) {
		this.status = status;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}
