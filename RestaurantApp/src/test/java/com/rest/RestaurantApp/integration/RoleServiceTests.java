package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.RoleService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RoleServiceTests {
	
	@Autowired
	private RoleService roleService;
	
	@Test
	void testfindByName_ValidName() {
		
		List<Role> result = roleService.findByName("ROLE_MANAGER");
		
		assertEquals(1, result.size());
	}
	
	@Test
	void testfindByName_InvalidName() {
		
		List<Role> result = roleService.findByName("xyz");
		
		assertEquals(0, result.size());
	}
	
	@Test
	void testfindById_ValidId() {
		
		Role result = roleService.findById(1);
		
		assertEquals("ROLE_MANAGER", result.getName());
	}
	
	@Test
	void testfindById_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			roleService.findById(100);
            });
	}
}
