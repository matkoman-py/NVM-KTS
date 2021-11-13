package com.rest.RestaurantApp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;

@Entity
@DiscriminatorValue("PRIVILEGED_USER")
@Where(clause = "deleted = false")
public class PrivilegedUser extends User {
	
	//@Column(nullable = false)
	private String username;
	
	//@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	//@Column(nullable = false)
	private PrivilegedUserType privilegedType;
	
	
	public PrivilegedUser() {
		super();
	}

	public PrivilegedUser(String email, String name, String surname, Date birthday, UserType type, String username,
			String password, PrivilegedUserType privilegedType) {
		super(email, name, surname, birthday, type);
		this.username = username;
		this.password = password;
		this.privilegedType = privilegedType;
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
