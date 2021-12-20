package com.rest.RestaurantApp.dto;

import java.util.Date;
import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
