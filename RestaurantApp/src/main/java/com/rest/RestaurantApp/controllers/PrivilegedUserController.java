package com.rest.RestaurantApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.services.PrivilegedUserService;

@RestController
@RequestMapping("/api/privilegedUser")
public class PrivilegedUserController {

	private PrivilegedUserService privilegedUserService;
	
	@Autowired
	public PrivilegedUserController(PrivilegedUserService privilegedUserService) {
		this.privilegedUserService = privilegedUserService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PrivilegedUserDTO>> getAll() {
		return ResponseEntity.ok(privilegedUserService.getAll());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrivilegedUserDTO> getOne(@PathVariable("id") int id) {
		PrivilegedUserDTO privilegedUser = privilegedUserService.getOne(id);
		if(privilegedUser == null) {
			return new ResponseEntity<PrivilegedUserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PrivilegedUserDTO>(privilegedUser, HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		PrivilegedUserDTO privilegedUser = privilegedUserService.delete(id);
		if(privilegedUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("PrivilegedUser with id " + id + " successfully deleted", HttpStatus.OK);	
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrivilegedUserDTO> create(@RequestBody PrivilegedUserDTO privilegedUser) {
		PrivilegedUserDTO createdPrivilegedUser = privilegedUserService.create(privilegedUser);
		return new ResponseEntity<PrivilegedUserDTO>(createdPrivilegedUser, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrivilegedUserDTO> update(@PathVariable("id") int id, @RequestBody PrivilegedUserDTO privilegedUser) {
		PrivilegedUserDTO updatedPrivilegedUser = privilegedUserService.update(id, privilegedUser);
		if(updatedPrivilegedUser == null) {
			return new ResponseEntity<PrivilegedUserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PrivilegedUserDTO>(updatedPrivilegedUser, HttpStatus.OK);
	}
}
