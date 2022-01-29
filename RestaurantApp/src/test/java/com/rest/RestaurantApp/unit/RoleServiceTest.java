package com.rest.RestaurantApp.unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.RoleRepository;
import static org.mockito.Mockito.when;
import com.rest.RestaurantApp.services.RoleService;

@SpringBootTest
public class RoleServiceTest {
	
	@MockBean
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;	
	
	@BeforeEach
	public void setup() {
		Role role1 = new Role();
		role1.setId(0);
		role1.setName("TEST_ROLE");
		List<Role> roles = new ArrayList<>();	
		roles.add(role1);
		
		when(roleRepository.findById(0)).thenReturn(Optional.of(role1));
		when(roleRepository.findById(5)).thenReturn(Optional.empty());   
		when(roleRepository.findByName("TEST_ROLE")).thenReturn(roles); 
		when(roleRepository.findByName("FAIL")).thenReturn(null);
	}                                                                       
	
	@Test
   	void testFindById_ValidId() {
		Role result = roleService.findById(0);
		assertEquals("TEST_ROLE", result.getName());
	}
	
	@Test
   	void testFindByName_Invalid() {
		assertEquals(null, roleService.findByName("FAIL"));
	}
	
	@Test
   	void testFindByName_Valid() {
		List<Role> result = roleService.findByName("TEST_ROLE");
		assertEquals(1, result.size());
	}
	
	@Test
   	void testFindById_Invalid() {
	assertThrows(NotFoundException.class, ()->{
		roleService.findById(100);
        });
	}
}