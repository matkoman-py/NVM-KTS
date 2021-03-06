package com.rest.RestaurantApp.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.exceptions.EmployeeCurrentlyWorkingException;
import com.rest.RestaurantApp.exceptions.EmployeeWithEmailAlreadyExists;
import com.rest.RestaurantApp.exceptions.EmployeeWithPinAlreadyExists;
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
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDTO>> search(@RequestParam(value = "name", required = false, defaultValue = "") String name,
													@RequestParam(value = "surname", required = false, defaultValue = "") String surname,
													@RequestParam(value = "email", required = false, defaultValue = "") String email,
													@RequestParam(value = "dateBefore", required = false, defaultValue = "") String dateBefore,
													@RequestParam(value = "dateAfter", required = false, defaultValue = "") String dateAfter) throws ParseException {
		return ResponseEntity.ok(employeeService.search(name, surname, email, dateBefore,dateAfter));	
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeDTO>> getAll() {
		return ResponseEntity.ok(employeeService.getAll());
	}
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> getOne(@PathVariable("id") int id) {
		EmployeeDTO employee = employeeService.getOne(id);
		return new ResponseEntity<EmployeeDTO>(employee, HttpStatus.OK);	
	}
	@PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> delete(@PathVariable("id") int id) {
		EmployeeDTO employee = employeeService.delete(id);
		return new ResponseEntity<EmployeeDTO>(employee, HttpStatus.OK);	
	}
	@PreAuthorize("hasRole('MANAGER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employee) {
		EmployeeDTO createdEmployee = employeeService.create(employee);
		return new ResponseEntity<EmployeeDTO>(createdEmployee, HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDTO> update(@PathVariable("id") int id, @RequestBody EmployeeDTO employee) {
		EmployeeDTO updatedEmployee = employeeService.update(id, employee);
		return new ResponseEntity<EmployeeDTO>(updatedEmployee, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping("/test_waiter/{pin}")
	public ResponseEntity<Boolean> checkIfWaiterPin(@PathVariable("pin") int pin) {
		boolean valid = employeeService.checkPin(pin, EmployeeType.WAITER);
		if(valid) return new ResponseEntity<>(true, HttpStatus.OK);
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getByPincode/{pin}")
	public ResponseEntity<Boolean> getByPincode(@PathVariable("pin") int pin) {
		boolean valid = employeeService.checkPin(pin, EmployeeType.WAITER);
		if(valid) return new ResponseEntity<>(true, HttpStatus.OK);
		return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = EmployeeWithEmailAlreadyExists.class)
	public ResponseEntity handleEmailAlreadyExistsException(EmployeeWithEmailAlreadyExists notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(value = EmployeeWithPinAlreadyExists.class)
	public ResponseEntity handlePincodeAlreadyExistsException(EmployeeWithPinAlreadyExists notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(value = EmployeeCurrentlyWorkingException.class)
	public ResponseEntity handleEmployeeCurrentlyWorkingException(EmployeeCurrentlyWorkingException employeeCurrentlyWorkingException) {
        return new ResponseEntity(employeeCurrentlyWorkingException.getMessage(), HttpStatus.CONFLICT);
    }
}
