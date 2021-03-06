package com.rest.RestaurantApp.domain;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;

@Entity
@DiscriminatorValue("EMPLOYEE")
@Where(clause = "deleted = false")
public class Employee extends User {
	
	//@Column(nullable = false)
	@Column(unique = true)
	private int pincode;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isFired;
	
	@Enumerated(EnumType.STRING)
	//@Column(nullable = false)
	private EmployeeType employeeType;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Order> orders;
	
	@OneToMany(mappedBy = "takenByEmployee")
	private List<OrderedArticle> takenArticles;
	
	public Employee(String email, String name, String surname, Date birthday, UserType type,
			int pincode, EmployeeType employeeType) {
		super(email, name, surname, birthday, type);
		this.pincode = pincode;
		this.employeeType = employeeType;
		this.orders = new ArrayList<>();
		this.takenArticles = new ArrayList<>();
	}

	public Employee() {
		super();
	}
	
	public void addOrder(Order order) {
		orders.add(order);
		order.setEmployee(this);
	}
	
	public void removeOrder(Order order) {
		order.setDeleted(true);
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(EmployeeType employeeType) {
		this.employeeType = employeeType;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderedArticle> getTakenArticles() {
		return takenArticles;
	}

	public void setTakenArticles(List<OrderedArticle> takenArticles) {
		this.takenArticles = takenArticles;
	}
	
	
	
	public boolean isFired() {
		return isFired;
	}

	public void setFired(boolean isFired) {
		this.isFired = isFired;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if (employee.getId() == null || this.getId() == null) {
            if(employee.getName().equals(this.getName())){
                return true;
            }
            return false;
        }
        return Objects.equals(this.getId(), employee.getId());
    }
}
