package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.*;
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
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.PrivilegedUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class PrivilegedUserServiceTest {
	
	@Autowired
	private PrivilegedUserService privilegedUserService;

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
		
		assertEquals(result.size(), 1);
	}
	
	@Test
	void testDelete_ValidId() {
		
		List<PrivilegedUserDTO> result1 = privilegedUserService.getAll();
		PrivilegedUserDTO employee = privilegedUserService.delete(2);
		List<PrivilegedUserDTO> result2 = privilegedUserService.getAll();
		
		assertNotEquals(result1.size(),result2.size());
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			privilegedUserService.delete(15);
		});
	}
}
