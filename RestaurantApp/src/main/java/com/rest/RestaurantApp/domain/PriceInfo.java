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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.PriceStatus;

@Entity
@Table(name = "prices")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
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
	@JoinColumn(name = "article_id", nullable = true)
	private Article article;

	public PriceInfo(Date fromDate, double makingPrice, double sellingPrice, Article article) {
		super();
		this.fromDate = fromDate;
		this.makingPrice = makingPrice;
		this.sellingPrice = sellingPrice;
		this.status = PriceStatus.ACTIVE;
		this.article = article;
	}

}
