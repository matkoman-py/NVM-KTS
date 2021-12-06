package com.rest.RestaurantApp.services;

import java.util.List;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.dto.EmployeeDTO;

public interface IEmployeeService {

	List<EmployeeDTO> getAll();
	
	EmployeeDTO getOne(int id);
	
	EmployeeDTO delete(int id);
	
	EmployeeDTO create(EmployeeDTO employee);
	
	EmployeeDTO update(int id, EmployeeDTO employee);

	boolean checkPin(int pin, EmployeeType type);

	EmployeeDTO getOneByPin(int pin);

}
