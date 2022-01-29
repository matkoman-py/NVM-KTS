package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

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

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
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
		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/employee/4", EmployeeDTO.class);

		EmployeeDTO employee = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(employee.getName(), "Marko");
	}
	
	@Test
	public void testGetOne_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/employee/15", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test 
	public void testGetAll() {
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity("/api/employee", EmployeeDTO[].class);
		
		EmployeeDTO[] employees = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(5, employees.length);
	}
	
	@Test
	public void testDelete_ValidId() { 

		EmployeeDTO employee = new EmployeeDTO(7,10000,"acafaca123@gmail.com","Aleksa","Kekezovic"
				,new Date(),UserType.EMPLOYEE, 56542, EmployeeType.COOK);

		ResponseEntity<EmployeeDTO> resEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee", HttpMethod.POST, new HttpEntity<EmployeeDTO>(employee), EmployeeDTO.class);
		
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/8", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testDelete_InvalidId() { 
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/15", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testDelete_InvalidEmployee_WaiterPreparingOrder() { 
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/employee/3", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals("Employee with id 3 is currently working on an order", responseEntity.getBody());
	}
	
	
	@Test
	public void testDelete_InvalidEmployee_CookOrBarmanPreparingArticle() { 
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/employee/7", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals("Employee with id 7 is currently working on an article", responseEntity.getBody());
	}
	
	
	@Test
	public void testcheckIfWaiterPin_ValidPinAndValidEmployeeType() {
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/test_waiter/1234", HttpMethod.GET, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testcheckIfWaiterPin_NonExistingPin() {
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/test_waiter/6666", HttpMethod.GET, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testcheckIfWaiterPin_ValidPinAndInvalidEmployeeType() {
		ResponseEntity<Boolean> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/test_waiter/2910", HttpMethod.GET, new HttpEntity<Object>(null), Boolean.class);
		
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdate_InvalidId() {
		EmployeeDTO employee = new EmployeeDTO(7,10000,"aca@gmail.com","Aleksa","Kekezovic",new Date(),UserType.EMPLOYEE, 1111, EmployeeType.COOK);

		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/15", HttpMethod.PUT, new HttpEntity<EmployeeDTO>(employee), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testUpdate_ValidId() {
		EmployeeDTO employee = new EmployeeDTO(7,10000,"aca@gmail.com","Aleksa","Kekezovic",new Date(),UserType.EMPLOYEE, 1111, EmployeeType.COOK);

		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee/4", HttpMethod.PUT, new HttpEntity<EmployeeDTO>(employee), EmployeeDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Aleksa", responseEntity.getBody().getName());
		assertEquals("Kekezovic", responseEntity.getBody().getSurname());
		assertEquals("aca@gmail.com", responseEntity.getBody().getEmail());
		assertEquals(EmployeeType.COOK, responseEntity.getBody().getEmployeeType());
		assertEquals(1111, responseEntity.getBody().getPincode());
	}
	
	@Test
	public void testCreate() {
		
		EmployeeDTO employee = new EmployeeDTO(7,10000,"acafaca@gmail.com","Aleksa","Kekezovic"
				,new Date(),UserType.EMPLOYEE, 9988, EmployeeType.COOK);

		ResponseEntity<EmployeeDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee", HttpMethod.POST, new HttpEntity<EmployeeDTO>(employee), EmployeeDTO.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Aleksa", employeeService.getOne(responseEntity.getBody().getId()).getName());
		assertEquals("Kekezovic", employeeService.getOne(responseEntity.getBody().getId()).getSurname());
		assertEquals("acafaca@gmail.com", employeeService.getOne(responseEntity.getBody().getId()).getEmail());
		assertEquals(EmployeeType.COOK, employeeService.getOne(responseEntity.getBody().getId()).getEmployeeType());
		assertEquals(9988, employeeService.getOne(responseEntity.getBody().getId()).getPincode());
	
		employeeService.delete(responseEntity.getBody().getId());
	}
	
	@Test
	public void testCreate_PincodeInUse() {
		
		EmployeeDTO employee = new EmployeeDTO(7,10000,"acafaca@gmail.com","Aleksa","Kekezovic"
				,new Date(),UserType.EMPLOYEE, 1234, EmployeeType.COOK);

		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee", HttpMethod.POST, new HttpEntity<EmployeeDTO>(employee), String.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
	
	@Test
	public void testCreate_EmailInUse() {
		
		EmployeeDTO employee = new EmployeeDTO(7,10000,"mateja99@yahoo.com","Aleksa","Kekezovic"
				,new Date(),UserType.EMPLOYEE, 1111, EmployeeType.COOK);

		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/employee", HttpMethod.POST, new HttpEntity<EmployeeDTO>(employee), String.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
}
