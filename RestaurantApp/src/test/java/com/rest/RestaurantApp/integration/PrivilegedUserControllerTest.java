package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.services.PrivilegedUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PrivilegedUserControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PrivilegedUserService privilegedUserService;
	
	@Test
	public void testGetOne_ValidId() throws Exception {
		ResponseEntity<PrivilegedUserDTO> responseEntity = restTemplate.getForEntity(
				"/api/privilegedUser/2", PrivilegedUserDTO.class);

		PrivilegedUserDTO privilegedUser = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Petar", privilegedUser.getName());
	}
	
	@Test
	public void testGetOne_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/api/privilegedUser/15", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test 
	public void testGetAll() {
		ResponseEntity<PrivilegedUserDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/privilegedUser", PrivilegedUserDTO[].class);
		
		PrivilegedUserDTO[] privilegedUsers = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, privilegedUsers.length);
	}
	
	@Test
	public void testDelete_ValidId() { 
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"acaXXaa@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameXX", "passwordX", PrivilegedUserType.MANAGER);
		privilegedUserService.create(pUserDTO);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/privilegedUser/" + pUserDTO.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testDelete_InvalidId() { 

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/privilegedUser/113", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdate_InvalidId() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"acaaaa@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameX", "passwordX", PrivilegedUserType.MANAGER);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/privilegedUser/15", HttpMethod.PUT, new HttpEntity<PrivilegedUserDTO>(pUserDTO), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdate_ValidId() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"acaaaa@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "usernameX", "passwordX", PrivilegedUserType.MANAGER);

		ResponseEntity<PrivilegedUserDTO> responseEntity = restTemplate.exchange(
				"/api/privilegedUser/2", HttpMethod.PUT, new HttpEntity<PrivilegedUserDTO>(pUserDTO), PrivilegedUserDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Aca", privilegedUserService.getOne(responseEntity.getBody().getId()).getName());
		assertEquals("Maca", privilegedUserService.getOne(responseEntity.getBody().getId()).getSurname());
		assertEquals("acaaaa@mail.com", privilegedUserService.getOne(responseEntity.getBody().getId()).getEmail());
		assertEquals("usernameX", privilegedUserService.getOne(responseEntity.getBody().getId()).getUsername());
		assertEquals("passwordX", privilegedUserService.getOne(responseEntity.getBody().getId()).getPassword());
	}
	
	@Test
	public void testCreate() {
		PrivilegedUserDTO pUserDTO = new PrivilegedUserDTO(2,10000,"a1caaaa@mail.com","Aca","Maca",new Date(),UserType.PRIVILEGED_USER, "username1X", "passwordX", PrivilegedUserType.MANAGER);

		ResponseEntity<PrivilegedUserDTO> responseEntity = restTemplate.exchange(
				"/api/privilegedUser", HttpMethod.POST, new HttpEntity<PrivilegedUserDTO>(pUserDTO), PrivilegedUserDTO.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Aca", privilegedUserService.getOne(responseEntity.getBody().getId()).getName());
		assertEquals("Maca", privilegedUserService.getOne(responseEntity.getBody().getId()).getSurname());
		assertEquals("a1caaaa@mail.com", privilegedUserService.getOne(responseEntity.getBody().getId()).getEmail());
		assertEquals("username1X", privilegedUserService.getOne(responseEntity.getBody().getId()).getUsername());
		assertEquals("passwordX", privilegedUserService.getOne(responseEntity.getBody().getId()).getPassword());
	
		privilegedUserService.delete(responseEntity.getBody().getId());
	}
}
