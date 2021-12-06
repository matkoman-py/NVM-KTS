package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.PrivilegedUserDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;
import com.rest.RestaurantApp.services.PrivilegedUserService;
import com.rest.RestaurantApp.services.RoleService;

@SpringBootTest
public class PrivilegedUserServiceTest {
	
	@MockBean
	private PriviligedUserRepository privilegedUserRepository;
	
	@MockBean
	private SalaryInfoRepository salaryInfoRepository;
	
	@MockBean
	private RoleService roleService;
	
	@Autowired
	private PrivilegedUserService privilegedUserService;
	
	@BeforeEach
	public void setup() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		List<SalaryInfo> salary2 = new ArrayList<>();
		List<SalaryInfo> salary3 = new ArrayList<>();
		List<PrivilegedUser> priviligedUsers = new ArrayList<>();
		
		PrivilegedUser priviligedUser = new PrivilegedUser("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(),UserType.PRIVILEGED_USER,"user","pass",PrivilegedUserType.MANAGER);
		priviligedUser.setId(1);	
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, priviligedUser);
		salary1.add(salaryInfo);
		priviligedUser.setSalaries(salary1);
		
		PrivilegedUser priviligedUser1 = new PrivilegedUser("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(),UserType.PRIVILEGED_USER,"user","pass",PrivilegedUserType.MANAGER);
		priviligedUser1.setId(2);
		salary2.add(new SalaryInfo(new Date(), 100000, priviligedUser1));
		priviligedUser1.setSalaries(salary2);
		
		PrivilegedUser priviligedUser2 = new PrivilegedUser("alek1andar.epic@gmail.com", "Aleks1andar", "C1epic", new GregorianCalendar(1999, 6, 19).getTime(),UserType.PRIVILEGED_USER,"user","pass",PrivilegedUserType.MANAGER);
		priviligedUser2.setId(3);
		salary3.add(new SalaryInfo(new Date(), 100000, priviligedUser2));
		priviligedUser2.setSalaries(salary3);
		
		
		priviligedUsers.add(priviligedUser);
		priviligedUsers.add(priviligedUser1);
		priviligedUsers.add(priviligedUser2);
		
		when(privilegedUserRepository.findAll()).thenReturn(priviligedUsers);
		given(privilegedUserRepository.findById(4)).willReturn(Optional.empty());
		given(privilegedUserRepository.findById(1)).willReturn(java.util.Optional.of(priviligedUser));
		given(privilegedUserRepository.save(priviligedUser)).willReturn(priviligedUser);
	}
	
	@Test
	void testGetAll() {
		List<PrivilegedUserDTO> returnedPrivilegedUsers = privilegedUserService.getAll();
		assertEquals(returnedPrivilegedUsers.size(), 3);
	}
	
	@Test
	void testGetOne_ValidId() {
		PrivilegedUserDTO result = privilegedUserService.getOne(1);
		assertEquals(result.getName(), "Petar");
	}
	
	@Test
	void testGetOne_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			PrivilegedUserDTO result = privilegedUserService.getOne(4);
            });
	}
	
	@Test
	void testDelete_ValidId() {
		
		PrivilegedUserDTO result = privilegedUserService.delete(1);
		
		assertEquals(result.getName(), "Petar");
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			PrivilegedUserDTO result = privilegedUserService.delete(4);
            });
	}
	
	@Test
	void testUpdate_ValidId() {
		
		PrivilegedUserDTO newPrivilegedUser = new PrivilegedUserDTO();
		newPrivilegedUser.setName("Aca");
		newPrivilegedUser.setSurname("Ceps");
		
		
		PrivilegedUserDTO result = privilegedUserService.update(1, newPrivilegedUser);
		assertEquals(result.getName(), "Aca");
		assertEquals(result.getSurname(), "Ceps");
	}
	
	@Test
	void testUpdate_InvalidId() {
		
		PrivilegedUserDTO newPrivilegedUser = new PrivilegedUserDTO();
		newPrivilegedUser.setName("Aca");
		newPrivilegedUser.setSurname("Ceps");
		
		assertThrows(NotFoundException.class, ()->{
			PrivilegedUserDTO result = privilegedUserService.update(15, newPrivilegedUser);
	        });
	}}
