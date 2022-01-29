package com.rest.RestaurantApp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("select e from Employee e where e.pincode = :pin and e.isFired=false")
	Optional<Employee> findByPincode(int pin);

	Optional<Employee> findByEmailAndIsFiredFalse(String email);
	
	@Override
	@Query("select e from Employee e where e.isFired=false")
	public List<Employee> findAll();


}
