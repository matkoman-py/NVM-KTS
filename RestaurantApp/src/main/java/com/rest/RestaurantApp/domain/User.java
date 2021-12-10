package com.rest.RestaurantApp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@NoArgsConstructor
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
	
	public void setNewSalary(SalaryInfo salaryInfo) {
		if(salaries.size() > 0) {
			SalaryInfo oldPrice = salaries.stream().filter(salary -> salary.getStatus().equals(SalaryStatus.ACTIVE)).findAny().orElse(null);
			oldPrice.setToDate(new Date());
			oldPrice.setStatus(SalaryStatus.EXPIRED);
		}
		salaries.add(salaryInfo);
	}
	
	public SalaryInfo getActiveSalary() {
		return salaries.stream().filter(salary -> salary.getStatus().equals(SalaryStatus.ACTIVE)).findAny().orElse(null);
	}

}
