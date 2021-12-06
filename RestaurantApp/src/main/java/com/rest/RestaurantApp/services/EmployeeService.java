package com.rest.RestaurantApp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
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
	
	private OrderRepository orderRepository;
	
	private OrderedArticleRepository orderedArticleRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, SalaryInfoRepository salaryInfoRepository, OrderRepository orderRepository,
			OrderedArticleRepository orderedArticleRepository) {
		this.employeeRepository = employeeRepository;
		this.salaryInfoRepository = salaryInfoRepository;
		this.orderRepository = orderRepository;
		this.orderedArticleRepository = orderedArticleRepository;
		
	}
	
	@Override
	public List<EmployeeDTO> getAll() {
		return employeeRepository.findAll().stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDTO getOne(int id) {
		Optional<Employee> employee =  employeeRepository.findById(id);
		if(employee.isEmpty()) {
			return null;
		}
		return new EmployeeDTO(employee.get());
	}

	@Override
	public EmployeeDTO delete(int id) {
		Optional<Employee> employeeData =  employeeRepository.findById(id);
		if(employeeData.isEmpty()) {
			return null;
		}
		Employee employee = employeeData.get();
		employee.setDeleted(true);
		employeeRepository.save(employee);
		return new EmployeeDTO(employee);
	}

	@Override
	public EmployeeDTO create(EmployeeDTO employee) {
		Employee newEmpolyee = new Employee(employee.getEmail(), employee.getName(), employee.getSurname(), 
				employee.getBirthday(), employee.getType(), employee.getPincode(), employee.getEmployeeType());
		SalaryInfo salaryInfo = new SalaryInfo(new Date(), employee.getSalary(), newEmpolyee);
		salaryInfoRepository.save(salaryInfo);
		newEmpolyee.setNewSalary(salaryInfo);
		return new EmployeeDTO(employeeRepository.save(newEmpolyee));
	}

	@Override
	public EmployeeDTO update(int id, EmployeeDTO employee) {
		Optional<Employee> oldEmployeeData = employeeRepository.findById(id);
		if(oldEmployeeData.isEmpty()) {
			return null;
		}
		Employee oldEmployee = oldEmployeeData.get();
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
		
		//TREBA ZA ORDERS I TAKEN ARTICLES DODATI
		return new EmployeeDTO(employeeRepository.save(oldEmployee));
	}

	@Override
	public boolean checkPin(int pin, EmployeeType type) {
		Employee e = employeeRepository.findByPincode(pin);

		return e != null && e.getEmployeeType().equals(type);
	}

	@Override
	public EmployeeDTO getOneByPin(int pin) {
		Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByPincode(pin));
		if(employee.isEmpty()) {
			return null;
		}
		return new EmployeeDTO(employee.get());
	}
}
