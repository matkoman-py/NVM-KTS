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
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.exceptions.EmployeeCurrentlyWorkingException;
import com.rest.RestaurantApp.exceptions.EmployeeWithEmailAlreadyExists;
import com.rest.RestaurantApp.exceptions.EmployeeWithPinAlreadyExists;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;
import com.rest.RestaurantApp.services.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private SalaryInfoRepository salaryInfoRepository;
	
	@MockBean
	private OrderedArticleRepository orderedArticleRepository;
	
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
		
		Employee employee4 = new Employee("cepic@yahoo.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee4.setId(1);	
		salary1.add(salaryInfo);
		employee4.setSalaries(salary1);
		
		Employee employeeInvalid = new Employee("cepic1@yahoo.com", "Pera", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 5432, EmployeeType.WAITER);
		employeeInvalid.setId(10);	
		salary1.add(salaryInfo);
		employeeInvalid.setSalaries(salary1);
		
		Employee employeeInvalid2 = new Employee("cepic21@yahoo.com", "Mirko", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 4328, EmployeeType.COOK);
		employeeInvalid.setId(11);	
		salary1.add(salaryInfo);
		employeeInvalid.setSalaries(salary1);
		
		employees.add(employee);
		employees.add(employee1);
		employees.add(employee2);
		
		when(employeeRepository.findAll()).thenReturn(employees);
		when(employeeRepository.findByEmailAndIsFiredFalse("cepic@yahoo.com")).thenReturn(Optional.of(employee4));
		given(employeeRepository.findById(4)).willReturn(Optional.empty());
		given(employeeRepository.findById(1)).willReturn(java.util.Optional.of(employee));
		given(employeeRepository.findById(10)).willReturn(java.util.Optional.of(employeeInvalid));
		given(employeeRepository.findById(11)).willReturn(java.util.Optional.of(employeeInvalid2));

		given(employeeRepository.save(employee)).willReturn(employee);
		given(employeeRepository.save(employee)).willReturn(employee);
		given(orderedArticleRepository.findByTakenByEmployeeIdAndStatusNot(10, ArticleStatus.FINISHED)).willThrow(EmployeeCurrentlyWorkingException.class);
		given(orderedArticleRepository.findByTakenByEmployeeIdAndStatusNot(11, ArticleStatus.FINISHED)).willThrow(EmployeeCurrentlyWorkingException.class);

		given(employeeRepository.findByPincode(1234)).willReturn(Optional.of(employee));
	}
	
	@Test
	void testCreate_PincodeInUse() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, employee);
		salary1.add(salaryInfo);
		employee.setSalaries(salary1);
		
		EmployeeDTO employee = new EmployeeDTO(null, 0, "acacepic@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);

		assertThrows(EmployeeWithPinAlreadyExists.class, ()->{
			employeeService.create(employee);
	        });
	}
	
	@Test
	void testCreate_EmailInUse() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, employee);
		salary1.add(salaryInfo);
		employee.setSalaries(salary1);
		
		EmployeeDTO employee = new EmployeeDTO(null, 0, "cepic@yahoo.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1994, EmployeeType.WAITER);

		assertThrows(EmployeeWithEmailAlreadyExists.class, ()->{
			employeeService.create(employee);
	        });
	}
	
	@Test
	void testCreate() {
		List<SalaryInfo> salary1 = new ArrayList<>();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), 100000, employee);
		salary1.add(salaryInfo);
		employee.setSalaries(salary1);
		
		EmployeeDTO employee = new EmployeeDTO(null, 0, "petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1994, EmployeeType.WAITER);
		
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
	void testDelete_InvalidEmployee_WaiterPreparingOrder() {
		
		assertThrows(EmployeeCurrentlyWorkingException.class, ()->{
			employeeService.delete(10);
            });
	}
	
	@Test
	void testDelete_InvalidEmployee_CookOrBarmanPreparingArticle() {
		
		assertThrows(EmployeeCurrentlyWorkingException.class, ()->{
			employeeService.delete(11);
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
