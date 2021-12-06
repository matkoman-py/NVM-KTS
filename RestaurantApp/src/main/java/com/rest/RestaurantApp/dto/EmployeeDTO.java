package com.rest.RestaurantApp.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;

public class EmployeeDTO {

	private Integer id;

	private double salary;
	
	private String email;
	
	private String name;

	private String surname;
	
	private Date birthday;
	
	private UserType type;
	
	private int pincode;
	
	private EmployeeType employeeType;

	//private List<OrderDTO> orders;
	
	//private List<OrderedArticleDTO> takenArticles;
	
	public EmployeeDTO(Integer id, double salary, String email, String name, String surname, Date birthday, UserType type, int pincode, EmployeeType employeeType,
			List<OrderDTO> orders, List<OrderedArticleDTO> takenArticles) {
		super();
		this.id = id;
		this.salary = salary;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.type = type;
		this.pincode = pincode;
		this.employeeType = employeeType;
		//this.orders = orders;
		//this.takenArticles = takenArticles;
	}
	
	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(Employee employee) {
		super();
		this.id = employee.getId();
		this.salary = employee.getActiveSalary().getValue();
		this.email = employee.getEmail();
		this.name = employee.getName();
		this.surname = employee.getSurname();
		this.birthday = employee.getBirthday();
		this.type = employee.getType();
		this.pincode = employee.getPincode();
		this.employeeType = employee.getEmployeeType();
		//this.orders = employee.getOrders().stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
		//this.takenArticles = employee.getTakenArticles().stream().map(orderedArticle -> new OrderedArticleDTO(orderedArticle)).collect(Collectors.toList());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
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
	/*
	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public List<OrderedArticleDTO> getTakenArticles() {
		return takenArticles;
	}

	public void setTakenArticles(List<OrderedArticleDTO> takenArticles) {
		this.takenArticles = takenArticles;
	}
	*/
}
