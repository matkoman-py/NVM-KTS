package com.rest.RestaurantApp.dto;

import java.time.LocalDateTime;import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.Order;


public class OrderDTO {
	
	private Integer id;
	private boolean deleted;
	private String description;
	private LocalDateTime orderDate;
	private List<Integer> orderedArticles;
	private int tableNumber;
	private int employeeId;
	
	public OrderDTO(Integer id, boolean deleted, String description, LocalDateTime orderDate, int tableNumber, int employeeId) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.tableNumber = tableNumber;
		this.employeeId = employeeId;
	}

	public OrderDTO() {
		super();
	}
	
	public OrderDTO(Order order) {
		super();
		this.id = order.getId();
		this.deleted = order.isDeleted();
		this.description = order.getDescription();
		this.orderDate = order.getOrderDate();
		this.tableNumber = order.getTableNumber();
		this.employeeId = order.getEmployee().getId();
		this.orderedArticles = order.getOrderedArticles().stream().map(article -> article.getArticle().getId()).collect(Collectors.toList());
	}
	
	

	public OrderDTO(Integer id, boolean deleted, String description, LocalDateTime orderDate,
			List<Integer> orderedArticles, int tableNumber, int employeeId) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.orderedArticles = orderedArticles;
		this.tableNumber = tableNumber;
		this.employeeId = employeeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public List<Integer> getOrderedArticles() {
		return orderedArticles;
	}

	public void setOrderedArticles(List<Integer> orderedArticles) {
		this.orderedArticles = orderedArticles;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
}
