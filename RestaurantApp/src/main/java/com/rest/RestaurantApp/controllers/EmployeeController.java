package com.rest.RestaurantApp.controllers;

import java.util.List;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDTO>> getAll() {
		return ResponseEntity.ok(employeeService.getAll());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> getOne(@PathVariable("id") int id) {
		EmployeeDTO employee = employeeService.getOne(id);
		/*
		if(employee == null) {
			return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<EmployeeDTO>(employee, HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		EmployeeDTO employee = employeeService.delete(id);
		/*
		if(employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<>("Employee with id " + id + " successfully deleted", HttpStatus.OK);	
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employee) {
		EmployeeDTO createdEmployee = employeeService.create(employee);
		return new ResponseEntity<EmployeeDTO>(createdEmployee, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> update(@PathVariable("id") int id, @RequestBody EmployeeDTO employee) {
		EmployeeDTO updatedEmployee = employeeService.update(id, employee);
		/*
		if(updatedEmployee == null) {
			return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<EmployeeDTO>(updatedEmployee, HttpStatus.OK);
	}

	@GetMapping("/test_waiter/{pin}")
	public ResponseEntity<Boolean> checkIfWaiterPin(@PathVariable("pin") int pin) {
		boolean pinValid = employeeService.checkPin(pin, EmployeeType.WAITER);
		if(pinValid) return new ResponseEntity<>(true, HttpStatus.OK);
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
