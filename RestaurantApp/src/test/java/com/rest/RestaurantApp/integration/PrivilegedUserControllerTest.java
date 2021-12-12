package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.services.PrivilegedUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
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
		assertEquals(privilegedUser.getName(), "Petar");
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
		assertEquals(privilegedUsers.length, 1);
	}
	
	@Test
	public void testDelete() { 
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/privilegedUser/1", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
