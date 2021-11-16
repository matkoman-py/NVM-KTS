package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByPincode(int pin);
}
