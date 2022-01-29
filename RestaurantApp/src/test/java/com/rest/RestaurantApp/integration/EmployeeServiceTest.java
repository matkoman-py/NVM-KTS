package com.rest.RestaurantApp.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.exceptions.EmployeeCurrentlyWorkingException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.EmployeeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	void testGetOne_ValidId() {
		
		EmployeeDTO result = employeeService.getOne(3);
		
		assertEquals("Mateja", result.getName());
	}
	
	@Test
	void testGetOne_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			employeeService.getOne(1);
            });
	}
	
	@Test
	void testGetAll() {
		
		List<EmployeeDTO> result = employeeService.getAll();
		
		assertEquals(5, result.size());
	}
	
	@Test
	void testDelete_ValidId() {
		
		EmployeeDTO employee = new EmployeeDTO(9,10000,"aca@gmail.com","Aleksa","Kekezovic",new Date(),UserType.EMPLOYEE, 9876, EmployeeType.COOK);
		employee = employeeService.create(employee);
		List<EmployeeDTO> result1 = employeeService.getAll();
		System.out.println(result1.size()+ " DASDSAD");
		employeeService.delete(employee.getId());
		List<EmployeeDTO> result2 = employeeService.getAll();
		System.out.println(result2.size());
		
		assertNotEquals(result1.size(),result2.size());
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			employeeService.delete(15);
		});
	}
	
	@Test
	void testDelete_InvalidEmployee_WaiterPreparingOrder() {
		
		assertThrows(EmployeeCurrentlyWorkingException.class,()->{
			employeeService.delete(3);
		});
	}
	
	@Test
	void testDelete_InvalidEmployee_CookOrBarmanPreparingArticle() {
		
		assertThrows(EmployeeCurrentlyWorkingException.class,()->{
			employeeService.delete(6);
		});
	}
	
	@Test
	void getOneByPin_ValidPin() {
		EmployeeAuthDTO employee = employeeService.getOneByPin(1234);
		
		assertEquals("mateja99@yahoo.com", employee.getEmail());
	}
	
	@Test
	void getOneByPin_InvalidPin() {
		assertThrows(NotFoundException.class, ()->{
			employeeService.getOneByPin(3322);
            });
	}
	
	@Test
	void checkPin_ValidCombination() {
		assertTrue(employeeService.checkPin(1234, EmployeeType.WAITER));
	}
	
	@Test
	void checkPin_InvalidCombination() {
		assertFalse(employeeService.checkPin(1234, EmployeeType.COOK));
	}
	
	@Test
	void checkPin_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			employeeService.checkPin(3322, EmployeeType.COOK);
            });
	}
	
	@Test
	void update_ValidId() {
		EmployeeDTO employee = new EmployeeDTO(8,10000,"aca1@gmail.com","Aleksa","Kekezovic",new Date(),UserType.EMPLOYEE, 1111, EmployeeType.COOK);
		employeeService.create(employee);
		
		EmployeeDTO updateEmployee = new EmployeeDTO(8,20000,"Uaca@gmail.com","UAleksa","UKekezovic",new Date(),UserType.EMPLOYEE, 1111, EmployeeType.WAITER);
		EmployeeDTO updatedEmployee = employeeService.update(8, updateEmployee);

		assertEquals(20000,updatedEmployee.getSalary());
		assertEquals("Uaca@gmail.com",updatedEmployee.getEmail());
		assertEquals("UAleksa",updatedEmployee.getName());
		assertEquals("UKekezovic",updatedEmployee.getSurname());
		assertEquals(1111,updatedEmployee.getPincode());
		assertEquals(EmployeeType.WAITER,updatedEmployee.getEmployeeType());
		
		employeeService.delete(8);
	}
	
	@Test
	void update_InvalidId() {
		EmployeeDTO updateEmployee = new EmployeeDTO(17,20000,"Uaca@gmail.com","UAleksa","UKekezovic",new Date(),UserType.EMPLOYEE, 1389, EmployeeType.WAITER);

		assertThrows(NotFoundException.class, ()->{
			employeeService.update(17, updateEmployee);
            });
	}
	
	@Test
	void testCreate() {
		int beforeCreate = employeeService.getAll().size();
		
		EmployeeDTO employee = new EmployeeDTO(10,10000,"aca2@gmail.com","Aleksa","Kekezovic",new Date(),UserType.EMPLOYEE, 1661, EmployeeType.COOK);
		employee = employeeService.create(employee);
		
		int afterCreate = employeeService.getAll().size();
		assertEquals(beforeCreate, afterCreate - 1);

		employeeService.delete(employee.getId());
	}
	
	@Test
	void testSearch_ExpectedNone() throws ParseException {
		List<EmployeeDTO> employees = employeeService.search("x", "y", "z", "", "");
		
		assertEquals(0, employees.size());
	}
	
	@Test
	void testSearch_ExpectedAll() throws ParseException {
		List<EmployeeDTO> employees = employeeService.search("", "", "", "", "");
		
		assertEquals(5, employees.size());
	}

	@Test
	void testSearch_ExpectedOne() throws ParseException {
		List<EmployeeDTO> employees = employeeService.search("Mateja", "Cosovic", "99@yahoo.com", "1888-8-1", "2021-10-3");
		
		assertEquals(1, employees.size());
	}
}
