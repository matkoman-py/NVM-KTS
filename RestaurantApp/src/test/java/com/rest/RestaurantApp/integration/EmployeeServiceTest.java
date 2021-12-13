package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.dto.EmployeeDTO;
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
		
		assertEquals(result.getName(), "Mateja");
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
		
		assertEquals(result.size(), 4);
	}
	
//	@Test
//	void testDelete_ValidId() {
//		
//		List<EmployeeDTO> result1 = employeeService.getAll();
//		EmployeeDTO employee = employeeService.delete(5);
//		List<EmployeeDTO> result2 = employeeService.getAll();
//		
//		assertNotEquals(result1.size(),result2.size());
//	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			employeeService.delete(15);
		});
	}
}
