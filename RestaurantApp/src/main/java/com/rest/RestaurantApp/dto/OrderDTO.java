package com.rest.RestaurantApp.dto;

import java.time.LocalDateTime;import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.BaseEntity;
import com.rest.RestaurantApp.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	
	private Integer id;
	private boolean deleted;
	private String description;
	private LocalDateTime orderDate;
	private List<Integer> articles;
	private List<Integer> orderedArticles;
	private int tableNumber;
	private int employeeId;

	public OrderDTO(boolean deleted, String description, LocalDateTime orderDate, List<Integer> articles,
			int tableNumber, int employeeId) {
		super();
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.articles = articles;
		this.tableNumber = tableNumber;
		this.employeeId = employeeId;
	}

	public OrderDTO(Order order) {
		super();
		this.id = order.getId();
		this.deleted = order.isDeleted();
		this.description = order.getDescription();
		this.orderDate = order.getOrderDate();
		this.tableNumber = order.getTableNumber();
		this.employeeId = order.getEmployee().getId();
		this.articles = order.getOrderedArticles().stream().map(article -> article.getArticle().getId()).collect(Collectors.toList());
		this.orderedArticles = order.getOrderedArticles().stream().map(BaseEntity::getId).collect(Collectors.toList());
	}

	public OrderDTO(Integer id, boolean deleted, String description, LocalDateTime orderDate,
			List<Integer> orderedArticles, int tableNumber, int employeeId) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.articles = orderedArticles;
		this.tableNumber = tableNumber;
		this.employeeId = employeeId;
	}

}
