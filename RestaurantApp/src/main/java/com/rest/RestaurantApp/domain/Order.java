package com.rest.RestaurantApp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "orders")
@Where(clause = "deleted = false")
public class Order extends BaseEntity{
	
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int tableNumber;
	
	@Column(nullable = false)
	private LocalDateTime orderDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderedArticle> orderedArticles;

	public Order(String description, int tableNumber, LocalDateTime orderDate, Employee employee) {
		super();
		this.description = description;
		this.tableNumber = tableNumber;
		this.orderDate = orderDate;
		this.employee = employee;
		this.orderedArticles = new ArrayList<>();
	}

	public Order() {
		super();
	}
	
	public void addOrderedArticle(OrderedArticle orderedArticle) {
        orderedArticles.add(orderedArticle);
        orderedArticle.setOrder(this);
    }
 
	public void removeOrderedArticle(OrderedArticle orderedArticle) {
        //orderedArticles.remove(orderedArticle);
        orderedArticle.setDeleted(true);
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<OrderedArticle> getOrderedArticles() {
		return orderedArticles;
	}

	public void setOrderedArticles(List<OrderedArticle> orderedArticles) {
		this.orderedArticles = orderedArticles;
	}
	
	
	
	
}
