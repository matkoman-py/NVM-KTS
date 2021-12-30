package com.rest.RestaurantApp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.PriceStatus;
import com.rest.RestaurantApp.domain.enums.SalaryStatus;
import com.rest.RestaurantApp.domain.enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Where(clause = "deleted = false")
public class User extends BaseEntity {
	
	@OneToMany(mappedBy = "user")
	private List<SalaryInfo> salaries;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false)
	private Date birthday;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", insertable = false, updatable = false)
	private UserType type;

	public User(String email, String name, String surname, Date birthday, UserType type) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.type = type;
		this.salaries = new ArrayList<SalaryInfo>();
	}

	public User() {
		
	}
	
	public void setNewSalary(SalaryInfo salaryInfo) {
		if(salaries.size() > 0) {
			SalaryInfo oldPrice = salaries.stream().filter(salary -> salary.getStatus().equals(SalaryStatus.ACTIVE)).findAny().orElse(null);
			oldPrice.setToDate(new Date());
			oldPrice.setStatus(SalaryStatus.EXPIRED);
		}
		salaryInfo.setStatus(SalaryStatus.ACTIVE);
		salaries.add(salaryInfo);
	}
	
	public SalaryInfo getActiveSalary() {
		return salaries.stream().filter(salary -> salary.getStatus().equals(SalaryStatus.ACTIVE)).findAny().orElse(null);
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
	
	public List<SalaryInfo> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<SalaryInfo> salaries) {
		this.salaries = salaries;
	}
}
