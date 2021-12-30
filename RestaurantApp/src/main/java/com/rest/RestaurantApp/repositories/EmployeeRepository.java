package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("select e from Employee e where e.pincode = :pin")
	Employee findByPincode(int pin);

}
