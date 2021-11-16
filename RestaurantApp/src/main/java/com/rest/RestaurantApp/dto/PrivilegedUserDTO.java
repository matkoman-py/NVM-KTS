package com.rest.RestaurantApp.dto;

import java.util.Date;
import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;

public class PrivilegedUserDTO {
	
	private Integer id;

	private double salary;
	
	private String email;
	
	private String name;

	private String surname;
	
	private Date birthday;
	
	private UserType type;
	
	private String username;
	
	private String password;
	
	private PrivilegedUserType privilegedType;

	public PrivilegedUserDTO(Integer id, double salary, String email, String name, String surname, Date birthday, UserType type, String username,
			String password, PrivilegedUserType privilegedType) {
		super();
		this.id = id;
		this.salary = salary;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.type = type;
		this.username = username;
		this.password = password;
		this.privilegedType = privilegedType;
	}
	
	public PrivilegedUserDTO(PrivilegedUser priviligedUser) {
		super();
		this.id = priviligedUser.getId();
		this.salary = priviligedUser.getActiveSalary().getValue();
		this.email = priviligedUser.getEmail();
		this.name = priviligedUser.getName();
		this.surname = priviligedUser.getSurname();
		this.birthday = priviligedUser.getBirthday();
		this.type = priviligedUser.getType();
		this.username = priviligedUser.getUsername();
		this.password = priviligedUser.getPassword();
		this.privilegedType = priviligedUser.getPrivilegedType();
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PrivilegedUserType getPrivilegedType() {
		return privilegedType;
	}

	public void setPrivilegedType(PrivilegedUserType privilegedType) {
		this.privilegedType = privilegedType;
	}
}
