package com.rest.RestaurantApp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
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
			return null;
		}
		return new PrivilegedUserDTO(privilegedUser.get());
	}

	@Override
	public PrivilegedUserDTO delete(int id) {
		Optional<PrivilegedUser> privilegedUserData = privilegedUserRepository.findById(id);
		if(privilegedUserData.isEmpty()) {
			return null;
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
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), privilegedUser.getSalary(), newPrivilegedUser);
		salaryInfoRepository.save(salaryInfo);
		newPrivilegedUser.setNewSalary(salaryInfo);
		List<Role> roles = roleService.findByName("MANAGER");
		newPrivilegedUser.setRoles(roles);
		return new PrivilegedUserDTO(privilegedUserRepository.save(newPrivilegedUser));
	}


	@Override
	public PrivilegedUserDTO update(int id, PrivilegedUserDTO privilegedUser) {
		Optional<PrivilegedUser> oldPrivilegedUserData = privilegedUserRepository.findById(id);
		if(oldPrivilegedUserData.isEmpty()) {
			return null;
		}
		PrivilegedUser oldPrivilegedUser = oldPrivilegedUserData.get();
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
