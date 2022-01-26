package com.rest.RestaurantApp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;
import com.rest.RestaurantApp.repositories.SalaryInfoRepository;

@Service
@Transactional
public class EmployeeService implements IEmployeeService{

	private EmployeeRepository employeeRepository;
	
	private SalaryInfoRepository salaryInfoRepository;


	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, SalaryInfoRepository salaryInfoRepository, OrderRepository orderRepository,
			OrderedArticleRepository orderedArticleRepository) {
		this.employeeRepository = employeeRepository;
		this.salaryInfoRepository = salaryInfoRepository;
	}
	
	@Override//
	public List<EmployeeDTO> getAll() {
		return employeeRepository.findAll().stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
	}

	@Override//
	public EmployeeDTO getOne(int id) {
		Optional<Employee> employee =  employeeRepository.findById(id);
		if(employee.isEmpty()) {
			throw new NotFoundException("Employee with id " + id + " was not found");
		}
		return new EmployeeDTO(employee.get());
	}

	@Override//
	public EmployeeDTO delete(int id) {
		Optional<Employee> employeeData =  employeeRepository.findById(id);
		if(employeeData.isEmpty()) {
			throw new NotFoundException("Employee with id " + id + " was not found");
		}
		Employee employee = employeeData.get();
		employee.setDeleted(true);
		employeeRepository.save(employee);
		return new EmployeeDTO(employee);
	}

	@Override//
	public EmployeeDTO create(EmployeeDTO employee) {
		Employee newEmpolyee = new Employee(employee.getEmail(), employee.getName(), employee.getSurname(), 
				employee.getBirthday(), employee.getType(), employee.getPincode(), employee.getEmployeeType());
		Employee createdEmployee = employeeRepository.save(newEmpolyee);
		
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), employee.getSalary(), newEmpolyee);
		salaryInfoRepository.save(salaryInfo);
		createdEmployee.setNewSalary(salaryInfo);
		
		Employee createdEmployeeWithSalary = employeeRepository.save(newEmpolyee);
		
		return new EmployeeDTO(createdEmployeeWithSalary);
	}

	@Override//
	public EmployeeDTO update(int id, EmployeeDTO employee) {
		Optional<Employee> oldEmployeeData = employeeRepository.findById(id);
		if(oldEmployeeData.isEmpty()) {
			throw new NotFoundException("Employee with id " + id + " was not found");
		}
		Employee oldEmployee = oldEmployeeData.get();
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), employee.getSalary(), oldEmployee);
		salaryInfoRepository.save(salaryInfo);
		oldEmployee.setNewSalary(salaryInfo);
		oldEmployee.setEmail(employee.getEmail());
		oldEmployee.setName(employee.getName());
		oldEmployee.setSurname(employee.getSurname());
		oldEmployee.setBirthday(employee.getBirthday());
		oldEmployee.setType(employee.getType());
		oldEmployee.setPincode(employee.getPincode());
		oldEmployee.setEmployeeType(employee.getEmployeeType());
		if(oldEmployee.getActiveSalary().getValue() != employee.getSalary()) {
			SalaryInfo newSalaryInfo = new SalaryInfo(new Date(), employee.getSalary(), oldEmployee);
			salaryInfoRepository.save(newSalaryInfo);
			oldEmployee.setNewSalary(newSalaryInfo);
		}
		
		return new EmployeeDTO(employeeRepository.save(oldEmployee));
	}

	@Override//
	public boolean checkPin(int pin, EmployeeType type) {
		Optional<Employee> e = employeeRepository.findByPincode(pin);
		
		if(e.isEmpty()) {
			throw new NotFoundException("Employee with pin " + pin + " was not found");
		}
		
		return e != null && e.get().getEmployeeType().equals(type);
	}

	@Override//
	public EmployeeAuthDTO getOneByPin(int pin) {
		Optional<Employee> employee = employeeRepository.findByPincode(pin);
		if(employee.isEmpty()) {
			throw new NotFoundException("Employee with pin " + pin + " was not found");
		}

		return new EmployeeAuthDTO(employee.get());
	}

	@Override
	public List<EmployeeDTO> search(String name, String surname, String email, String dateBefore, String dateAfter) throws ParseException {
		List<EmployeeDTO> employees = getAll();
		List<EmployeeDTO> employeesToCut;
		if(name != "") {
			employeesToCut = new ArrayList<>();
			for(EmployeeDTO e:employees) {
				if(!e.getName().toLowerCase().contains(name.toLowerCase())) employeesToCut.add(e);
			}
			employees.removeAll(employeesToCut);
		}
		if(surname != "") {
			employeesToCut = new ArrayList<>();
			for(EmployeeDTO e:employees) {
				if(!e.getSurname().toLowerCase().contains(surname.toLowerCase())) employeesToCut.add(e);
			}
			employees.removeAll(employeesToCut);
		}
		if(email != "") {
			employeesToCut = new ArrayList<>();
			for(EmployeeDTO e:employees) {
				if(!e.getEmail().toLowerCase().contains(email.toLowerCase())) employeesToCut.add(e);
			}
			employees.removeAll(employeesToCut);
		}


		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = formatter.parse("1900-01-01");
		Date dateTo = new Date();
		if(!dateBefore.equals("")) {
			dateFrom = formatter.parse(dateBefore);
		}
		if(!dateAfter.equals("")) {
			dateTo = formatter.parse(dateAfter);
		}
		
		employeesToCut = new ArrayList<>();
		for(EmployeeDTO e:employees) {
			
			if(e.getBirthday().after(dateTo) || e.getBirthday().before(dateFrom)) {
				employeesToCut.add(e);
			}
			
		}
		employees.removeAll(employeesToCut);
		System.out.println(dateBefore + " ODD");
		System.out.println(dateAfter + " DOO");
		System.out.println("DASDSADSA");
		
		return employees;
	}
}
