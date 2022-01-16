package com.rest.RestaurantApp.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;
import com.rest.RestaurantApp.services.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private SalaryInfoRepository salaryInfoRepository;
	
	@MockBean
	private Employee employee;
	
	@Autowired
	private EmployeeService employeeService;
	
	@BeforeEach
	public void setup() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		List<SalaryInfo> salary2 = new ArrayList<>();
		List<SalaryInfo> salary3 = new ArrayList<>();
		List<Employee> employees = new ArrayList<>();
		
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);	
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, employee);
		salary1.add(salaryInfo);
		employee.setSalaries(salary1);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		salary2.add(new SalaryInfo(new Date(), 100000, employee1));
		employee1.setSalaries(salary2);
		
		Employee employee2 = new Employee("alek1andar.epic@gmail.com", "Aleks1andar", "C1epic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2435, EmployeeType.WAITER);
		employee2.setId(3);
		salary3.add(new SalaryInfo(new Date(), 100000, employee2));
		employee2.setSalaries(salary3);
		
		
		employees.add(employee);
		employees.add(employee1);
		employees.add(employee2);
		
		when(employeeRepository.findAll()).thenReturn(employees);
		given(employeeRepository.findById(4)).willReturn(Optional.empty());
		given(employeeRepository.findById(1)).willReturn(java.util.Optional.of(employee));
		given(employeeRepository.save(employee)).willReturn(employee);
		
		given(employeeRepository.findByPincode(1234)).willReturn(Optional.of(employee));

	}
	
	@Test
	void testCreate() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, employee);
		salary1.add(salaryInfo);
		employee.setSalaries(salary1);
		
		EmployeeDTO employee = new EmployeeDTO(null, 0, "petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		
		EmployeeDTO createdEmployee = employeeService.create(employee);
		assertEquals("Petar", employeeService.getOne(createdEmployee.getId()).getName());
	}
	
	@Test
	void testGetAll() {
		List<EmployeeDTO> returnedEmployees = employeeService.getAll();
		assertEquals(3, returnedEmployees.size());
	}
	
	@Test
	void testGetOne_ValidId() {
		EmployeeDTO result = employeeService.getOne(1);
		assertEquals("Petar", result.getName());
	}
	
	@Test
	void testGetOne_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			employeeService.getOne(4);
            });
	}
	
	@Test
	void testDelete_ValidId() {
		
		EmployeeDTO result = employeeService.delete(1);
		
		assertEquals("Petar", result.getName());
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			employeeService.delete(4);
            });
	}
	
	@Test
	void testUpdate_ValidId() {
		
		EmployeeDTO newEmployee = new EmployeeDTO();
		newEmployee.setName("Aca");
		newEmployee.setSurname("Ceps");
		
		
		EmployeeDTO result = employeeService.update(1, newEmployee);
		assertEquals(result.getName(), "Aca");
		assertEquals(result.getSurname(), "Ceps");
	}
	
	@Test
	void testUpdate_InvalidId() {
		
		EmployeeDTO newEmployee = new EmployeeDTO();
		
		assertThrows(NotFoundException.class, ()->{
			employeeService.update(15, newEmployee);
	        });
	}
	
	@Test
	void getOneByPin_ValidPin() {
		EmployeeAuthDTO employee = employeeService.getOneByPin(1234);
		
		assertEquals("petarns99@gmail.com", employee.getEmail());
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
	
}
