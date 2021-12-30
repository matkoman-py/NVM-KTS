package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import com.rest.RestaurantApp.services.PrivilegedUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PrivilegedUserServiceTest {
	
	@Autowired
	private PrivilegedUserService privilegedUserService;

	@Autowired
	private PriviligedUserRepository privilegedUserRepository;

	@Test
	void testGetOne_ValidId() {
		
		PrivilegedUserDTO result = privilegedUserService.getOne(2);
		
		assertEquals(result.getName(), "Petar");
	}
	
	@Test
	void testGetOne_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			privilegedUserService.getOne(3);
            });
	}
	
	@Test
	void testGetAll() {
		
		List<PrivilegedUserDTO> result = privilegedUserService.getAll();
		
		assertEquals(2, result.size());
	}
	
	@Test
	void testDelete_ValidId() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"aca@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameX", "passwordX", PrivilegedUserType.MANAGER);
		privilegedUserService.create(pUserDTO);
		List<PrivilegedUserDTO> result1 = privilegedUserService.getAll();
		privilegedUserService.delete(2);
		List<PrivilegedUserDTO> result2 = privilegedUserService.getAll();
		
		assertNotEquals(result1.size(),result2.size());
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			privilegedUserService.delete(15);
		});
	}
	
	@Test
	void testUpdate_ValidId() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"aca@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameX", "passwordX", PrivilegedUserType.MANAGER);
		PrivilegedUserDTO createdPUserDTO = privilegedUserService.create(pUserDTO);
		
		PrivilegedUserDTO updateUserDTO = new PrivilegedUserDTO(2,20000,"Uaca@mail.com","UAca","UMaca",new Date(),UserType.PRIVILEGED_USER, "UusernameX", "UpasswordX", PrivilegedUserType.MANAGER);
		PrivilegedUserDTO updatedPUserDTO = privilegedUserService.update(createdPUserDTO.getId(), updateUserDTO);
	
		assertEquals(20000,updatedPUserDTO.getSalary());
		assertEquals("Uaca@mail.com",updatedPUserDTO.getEmail());
		assertEquals("UAca",updatedPUserDTO.getName());
		assertEquals("UMaca",updatedPUserDTO.getSurname());
		assertEquals("UusernameX",updatedPUserDTO.getUsername());
		assertEquals("UpasswordX",updatedPUserDTO.getPassword());

		privilegedUserService.delete(createdPUserDTO.getId());
	}
	
	@Test
	void testUpdate_InvalidId() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"aca@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameX", "passwordX", PrivilegedUserType.MANAGER);
		
		assertThrows(NotFoundException.class, ()->{
			privilegedUserService.update(17, pUserDTO);
            });
	}
	
	@Test
	void testCreate() {
		int beforeCreate = privilegedUserService.getAll().size();
		
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(3,10000,"aca1@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "username1X", "passwordX", PrivilegedUserType.MANAGER);
		PrivilegedUserDTO createdPUser = privilegedUserService.create(pUserDTO);
	
		int afterCreate = privilegedUserService.getAll().size();
		assertEquals(beforeCreate, afterCreate - 1);
		
		privilegedUserService.delete(createdPUser.getId());
	}
}
