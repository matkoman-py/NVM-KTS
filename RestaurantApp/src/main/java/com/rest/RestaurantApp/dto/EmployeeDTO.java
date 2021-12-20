package com.rest.RestaurantApp.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	private List<OrderDTO> orders;
	
	private List<OrderedArticleDTO> takenArticles;
	
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
		this.orders = employee.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
		this.takenArticles = employee.getTakenArticles().stream().map(OrderedArticleDTO::new).collect(Collectors.toList());
	}

}
