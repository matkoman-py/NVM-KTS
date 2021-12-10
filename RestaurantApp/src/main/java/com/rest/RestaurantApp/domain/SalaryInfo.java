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

import com.rest.RestaurantApp.domain.enums.SalaryStatus;

@Entity
@Table(name = "salaries")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
public class SalaryInfo extends BaseEntity {
	
	
	@Column(nullable = false)
	private Date fromDate;
	
	@Column
	private Date toDate;
	
	@Column(nullable = false)
	private double value;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SalaryStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public SalaryInfo(Date from, double value, User user) {
		super();
		this.fromDate = from;
		this.value = value;
		this.status = SalaryStatus.ACTIVE;
		this.user = user;
	}

}
