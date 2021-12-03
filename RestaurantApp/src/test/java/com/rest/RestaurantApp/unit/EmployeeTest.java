package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;
import com.rest.RestaurantApp.services.EmployeeService;

@SpringBootTest
public class EmployeeTest {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private SalaryInfoRepository salaryInfoRepository;
	
	@InjectMocks
	private EmployeeService employeeService;
	
	@Test 
	void testFindOneEmployee() {
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);
		
		ArrayList<SalaryInfo> salary = new ArrayList<SalaryInfo>();
		salary.add(new SalaryInfo(new Date(), 100000, employee));
		
		employee.setSalaries(salary);
		Optional<Employee> employeeData = Optional.of(employee);
		
		when(employeeRepository.findById(employee.getId())).thenReturn(employeeData);
		
		EmployeeDTO returnedEmployee = employeeService.getOne(employee.getId());
		
		assertEquals(returnedEmployee.getName(), employee.getName());
		assertEquals(returnedEmployee.getSurname(), employee.getSurname());
		assertEquals(returnedEmployee.getEmail(), employee.getEmail());
		assertEquals(returnedEmployee.getEmployeeType(), employee.getEmployeeType());
		assertEquals(returnedEmployee.getPincode(), employee.getPincode());

	}
	@Test
	void testFindAllEmployees() {
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);
		
		ArrayList<SalaryInfo> salary = new ArrayList<SalaryInfo>();
		salary.add(new SalaryInfo(new Date(), 100000, employee));
		
		employee.setSalaries(salary);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		ArrayList<SalaryInfo> salary1 = new ArrayList<SalaryInfo>();
		salary1.add(new SalaryInfo(new Date(), 100000, employee1));
		
		employee1.setSalaries(salary1);
		
		List<Employee> employees = Arrays.asList(employee, employee1);
		
		when(employeeRepository.findAll()).thenReturn(employees);
		
		List<EmployeeDTO> returnedEmployees = employeeService.getAll();
		
		assertEquals(returnedEmployees.size(), employees.size());
	}
	@Test
	void testDeleteOneEmployee() {
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);
		
		ArrayList<SalaryInfo> salary = new ArrayList<SalaryInfo>();
		salary.add(new SalaryInfo(new Date(), 100000, employee));
		
		employee.setSalaries(salary);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		ArrayList<SalaryInfo> salary1 = new ArrayList<SalaryInfo>();
		salary1.add(new SalaryInfo(new Date(), 100000, employee1));
		
		employee1.setSalaries(salary1);
		List<Employee> employees = Arrays.asList(employee, employee1);
		
		when(employeeRepository.findAll()).thenReturn(employees);
		employeeService.delete(employee.getId());

		Optional<Employee> employeeData = Optional.of(employee);
		when(employeeRepository.findById(employee.getId())).thenReturn(employeeData);
		
		assertEquals(employeeData.get().isDeleted(), true);
	}
}
