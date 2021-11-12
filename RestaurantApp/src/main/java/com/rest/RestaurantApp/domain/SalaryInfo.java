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

import com.rest.RestaurantApp.domain.enums.SalaryStatus;

@Entity
@Table(name = "salaries")
@Where(clause = "deleted = false")
public class SalaryInfo extends BaseEntity {
	
	
	@Column(nullable = false)
	private Date fromDate;
	
	@Column(nullable = false)
	private Date toDate;
	
	@Column(nullable = false)
	private double value;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SalaryStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public SalaryInfo(Date from, Date to, double value, SalaryStatus status, User user) {
		super();
		this.fromDate = from;
		this.toDate = to;
		this.value = value;
		this.status = status;
		this.user = user;
	}

	public SalaryInfo() {
		
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date from) {
		this.fromDate = from;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to) {
		this.toDate = to;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public SalaryStatus getStatus() {
		return status;
	}

	public void setStatus(SalaryStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
