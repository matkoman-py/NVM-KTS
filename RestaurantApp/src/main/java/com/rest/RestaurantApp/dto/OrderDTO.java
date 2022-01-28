package com.rest.RestaurantApp.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.enums.OrderStatus;


public class OrderDTO {
	
	private Integer id;
	private boolean deleted;
	private String description;
	private LocalDateTime orderDate;
	private List<Integer> articles;
	private List<Integer> orderedArticles;
	private int tableNumber;
	private int employeePin;
	private OrderStatus orderStatus;
	private double price;
	
	public OrderDTO(Integer id, boolean deleted, String description, LocalDateTime orderDate, int tableNumber, int employeePin, OrderStatus orderStatus, double price) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.tableNumber = tableNumber;
		this.employeePin = employeePin;
		this.orderStatus = orderStatus;
		this.price = price;
	}

	public OrderDTO(boolean deleted, String description, LocalDateTime orderDate, List<Integer> articles,
			int tableNumber, int employeePin, OrderStatus orderStatus, double price) {
		super();
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.articles = articles;
		this.tableNumber = tableNumber;
		this.employeePin = employeePin;
		this.orderStatus = orderStatus;
		this.price = price;
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
		this.employeePin = order.getEmployee().getPincode();
		this.articles = order.getOrderedArticles().stream().map(article -> article.getArticle().getId()).collect(Collectors.toList());
		this.orderedArticles = order.getOrderedArticles().stream().map(article -> article.getId()).collect(Collectors.toList());
		this.orderStatus = order.getOrderStatus();
		this.price = order.getPrice();
	}

	public OrderDTO(Integer id, boolean deleted, String description, LocalDateTime orderDate,
			List<Integer> orderedArticles, int tableNumber, int employeePin, OrderStatus orderStatus, double price) {
		super();
		this.id = id;
		this.deleted = deleted;
		this.description = description;
		this.orderDate = orderDate;
		this.articles = orderedArticles;
		this.tableNumber = tableNumber;
		this.employeePin = employeePin;
		this.orderStatus = orderStatus;
		this.price = price;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	
	public List<Integer> getArticles() {
		return articles;
	}

	public void setArticles(List<Integer> orderedArticles) {
		this.articles = orderedArticles;
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

	public int getEmployeePin() {
		return employeePin;
	}

	public void setEmployeePin(int employeePin) {
		this.employeePin = employeePin;
	}

	public List<Integer> getOrderedArticles() {
		return orderedArticles;
	}

	public void setOrderedArticles(List<Integer> orderedArticles) {
		this.orderedArticles = orderedArticles;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
