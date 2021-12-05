package com.rest.RestaurantApp.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;


@MappedSuperclass
@Where(clause = "deleted = false")
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "boolean default false", nullable = false)
	protected boolean deleted;
	
	public Integer getId() {
		return id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
