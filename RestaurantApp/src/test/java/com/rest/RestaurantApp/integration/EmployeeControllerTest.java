package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.services.EmployeeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EmployeeControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testGetOne_ValidId() throws Exception {
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.getForEntity(
				"/api/employee/4", EmployeeDTO.class);

		EmployeeDTO employee = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(employee.getName(), "Marko");
	}
	
	@Test
	public void testGetOne_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/api/employee/15", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test 
	public void testGetAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.getForEntity("/api/employee", EmployeeDTO[].class);
		
		EmployeeDTO[] employees = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(employees.length, 4);
	}
	
	@Test
	public void testDelete() { 
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/employee/3", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
