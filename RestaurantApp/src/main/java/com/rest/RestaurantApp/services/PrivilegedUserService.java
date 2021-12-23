package com.rest.RestaurantApp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;

@Service
@Transactional
public class PrivilegedUserService implements IPrivilegedUserService{


	private PriviligedUserRepository privilegedUserRepository;

	private SalaryInfoRepository salaryInfoRepository;

	private RoleService roleService;
	
	@Autowired
	public PrivilegedUserService(PriviligedUserRepository priviligedUserRepository, SalaryInfoRepository salaryInfoRepository) {
		this.privilegedUserRepository = priviligedUserRepository;
		this.salaryInfoRepository = salaryInfoRepository;
	}
	
	@Override
	public List<PrivilegedUserDTO> getAll() {
		return privilegedUserRepository.findAll().stream().map(privilegedUser -> new PrivilegedUserDTO(privilegedUser)).collect(Collectors.toList());
	}

	@Override
	public PrivilegedUserDTO getOne(int id) {
		Optional<PrivilegedUser> privilegedUser = privilegedUserRepository.findById(id);
		if(privilegedUser.isEmpty()) {
			throw new NotFoundException("Privileged user with id " + id + " was not found");
		}
		return new PrivilegedUserDTO(privilegedUser.get());
	}

	@Override
	public PrivilegedUserDTO delete(int id) {
		Optional<PrivilegedUser> privilegedUserData = privilegedUserRepository.findById(id);
		if(privilegedUserData.isEmpty()) {
			throw new NotFoundException("Privileged user with id " + id + " was not found");
		}
		PrivilegedUser privilegedUser = privilegedUserData.get();
		privilegedUser.setDeleted(true);
		privilegedUserRepository.save(privilegedUser);
		return new PrivilegedUserDTO(privilegedUser);
	}

	@Override
	public PrivilegedUserDTO create(PrivilegedUserDTO privilegedUser) {
		PrivilegedUser newPrivilegedUser = new PrivilegedUser(privilegedUser.getEmail(), privilegedUser.getName(), privilegedUser.getSurname(), 
				privilegedUser.getBirthday(), privilegedUser.getType(), privilegedUser.getUsername(), privilegedUser.getPassword(), privilegedUser.getPrivilegedType());
		
		PrivilegedUser createdPrivilegedUser = privilegedUserRepository.save(newPrivilegedUser);
		
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), privilegedUser.getSalary(), newPrivilegedUser);
		salaryInfoRepository.save(salaryInfo);
		createdPrivilegedUser.setNewSalary(salaryInfo);
		
		//List<Role> roles = roleService.findByName("MANAGER");
		//createdPrivilegedUser.setRoles(roles);
		
		PrivilegedUser createdPrivilegedUserWithSalary = privilegedUserRepository.save(createdPrivilegedUser);
		return new PrivilegedUserDTO(createdPrivilegedUserWithSalary);
	}
	
	@Override
	public PrivilegedUserDTO update(int id, PrivilegedUserDTO privilegedUser) {
		Optional<PrivilegedUser> oldPrivilegedUserData = privilegedUserRepository.findById(id);
		if(oldPrivilegedUserData.isEmpty()) {
			throw new NotFoundException("Privileged user with id " + id + " was not found");
		}
		PrivilegedUser oldPrivilegedUser = oldPrivilegedUserData.get();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), privilegedUser.getSalary(), oldPrivilegedUser);
		salaryInfoRepository.save(salaryInfo);
		oldPrivilegedUser.setNewSalary(salaryInfo);
		oldPrivilegedUser.setEmail(privilegedUser.getEmail());
		oldPrivilegedUser.setName(privilegedUser.getName());
		oldPrivilegedUser.setSurname(privilegedUser.getSurname());
		oldPrivilegedUser.setBirthday(privilegedUser.getBirthday());
		oldPrivilegedUser.setType(privilegedUser.getType());
		oldPrivilegedUser.setUsername(privilegedUser.getUsername());
		oldPrivilegedUser.setPassword(privilegedUser.getPassword());
		oldPrivilegedUser.setPrivilegedType(privilegedUser.getPrivilegedType());
		if(oldPrivilegedUser.getActiveSalary().getValue() != privilegedUser.getSalary()) {
			SalaryInfo newSalaryInfo = new SalaryInfo(new Date(), privilegedUser.getSalary(), oldPrivilegedUser);
			salaryInfoRepository.save(newSalaryInfo);
			oldPrivilegedUser.setNewSalary(newSalaryInfo);
		}
		return new PrivilegedUserDTO(privilegedUserRepository.save(oldPrivilegedUser));
	}

}
