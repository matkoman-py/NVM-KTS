package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.hibernate.transform.ToListResultTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;
import com.rest.RestaurantApp.exceptions.ChangeFinishedStateException;
import com.rest.RestaurantApp.exceptions.IncompatibleEmployeeTypeException;
import com.rest.RestaurantApp.exceptions.OrderAlreadyTakenException;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;
import com.rest.RestaurantApp.services.OrderService;


@SpringBootTest
class OrderTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderedArticleRepository orderedArticleRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	
	@InjectMocks
	private OrderService orderService;
	
	
	
	@Test
	void testFindOneOrder() {
		
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), employee);
		order.setId(1);
		
		Optional<Order> orderData = Optional.of(order);
		
		when(orderRepository.findById(order.getId())).thenReturn(orderData);
		
		OrderDTO returnedOrder = orderService.getOne(order.getId());
		
		assertEquals(returnedOrder.getDescription(), order.getDescription());
		assertEquals(returnedOrder.getEmployeeId(), order.getEmployee().getId());
		assertEquals(returnedOrder.getOrderDate(), order.getOrderDate());
		assertEquals(returnedOrder.getTableNumber(), order.getTableNumber());
	}
	
	@Test
	void testFindAllOrders() {
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		employee.setId(1);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), employee);
		order.setId(1);
		
		Order order1 = new Order("", 22, LocalDateTime.of(2021, 4, 5, 17, 9, 12), employee1);
		order1.setId(2);
		
		Order order2 = new Order("Join tables", 1, LocalDateTime.of(2021, 12, 13, 17, 0, 2), employee);
		order2.setId(3);
		
		List<Order> orders = Arrays.asList(order, order1, order2);
		
		when(orderRepository.findAll()).thenReturn(orders);
		
		List<OrderDTO> returnedOrders = orderService.getAll();
		
		assertEquals(returnedOrders.size(), orders.size());
	}
	
	@Test
	void testThrowsIncompatibleEmployeeTypeExceptionForChangeArticleStatus() {
		Article article = new Article("Pizza capriciosa", "basic pizza", ArticleType.MAIN_COURSE);
		article.setId(1); 
		
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.COOK);
		employee.setId(1);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), employee1);
		order.setId(1);
		
		OrderedArticle orderedArticle = new OrderedArticle(ArticleStatus.TAKEN, article, order, "");
		orderedArticle.setId(1);
		orderedArticle.setTakenByEmployee(employee);
		
		
		
		when(orderedArticleRepository.findById(orderedArticle.getId())).thenReturn(Optional.of(orderedArticle));
		when(employeeRepository.findByPincode(employee1.getPincode())).thenReturn(employee1);
		
		assertThrows(IncompatibleEmployeeTypeException.class, () -> {OrderedArticleDTO changedArticle = orderService.changeStatusOfArticle(employee1.getPincode(),orderedArticle.getId());});
		
	}
	
	@Test
	void testThrowsOrderAlreadyTakenExceptionForChangeArticleStatus() {
		Article article = new Article("Pizza capriciosa", "basic pizza", ArticleType.MAIN_COURSE);
		article.setId(1); 
		
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.COOK);
		employee.setId(1);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		Employee employee2 = new Employee("matkoman@gmail.com", "Mateja", "Cosovic", new GregorianCalendar(1999, 3, 19).getTime(), UserType.EMPLOYEE, 4321, EmployeeType.COOK);
		employee2.setId(3);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), employee1);
		order.setId(1);
		
		OrderedArticle orderedArticle = new OrderedArticle(ArticleStatus.TAKEN, article, order, "");
		orderedArticle.setId(1);
		orderedArticle.setTakenByEmployee(employee);
		
		
		
		when(orderedArticleRepository.findById(orderedArticle.getId())).thenReturn(Optional.of(orderedArticle));
		when(employeeRepository.findByPincode(employee2.getPincode())).thenReturn(employee2);
		
		assertThrows(OrderAlreadyTakenException.class, () -> {OrderedArticleDTO changedArticle = orderService.changeStatusOfArticle(employee2.getPincode(),orderedArticle.getId());});
		
	}
	
	@Test
	void testThrowsChangeFinishedStateExceptionForChangeArticleStatus() {
		Article article = new Article("Pizza capriciosa", "basic pizza", ArticleType.MAIN_COURSE);
		article.setId(1); 
		
		Employee employee = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.COOK);
		employee.setId(1);
		
		Employee employee1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		employee1.setId(2);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), employee1);
		order.setId(1);
		
		OrderedArticle orderedArticle = new OrderedArticle(ArticleStatus.FINISHED, article, order, "");
		orderedArticle.setId(1);
		orderedArticle.setTakenByEmployee(employee);
		
		
		
		when(orderedArticleRepository.findById(orderedArticle.getId())).thenReturn(Optional.of(orderedArticle));
		when(employeeRepository.findByPincode(employee1.getPincode())).thenReturn(employee1);
		
		assertThrows(ChangeFinishedStateException.class, () -> {OrderedArticleDTO changedArticle = orderService.changeStatusOfArticle(employee1.getPincode(),orderedArticle.getId());});
		
	}
	

}
