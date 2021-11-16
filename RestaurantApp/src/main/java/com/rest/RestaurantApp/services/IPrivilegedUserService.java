package com.rest.RestaurantApp.services;

import java.util.List;

import com.rest.RestaurantApp.dto.PrivilegedUserDTO;

public interface IPrivilegedUserService {

	List<PrivilegedUserDTO> getAll();
	
	PrivilegedUserDTO getOne(int id);
	
	PrivilegedUserDTO delete(int id);
	
	PrivilegedUserDTO create(PrivilegedUserDTO priviligedUser);
	
	PrivilegedUserDTO update(int id, PrivilegedUserDTO priviligedUser);
}
