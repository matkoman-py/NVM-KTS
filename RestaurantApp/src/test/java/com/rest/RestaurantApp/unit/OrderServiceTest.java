package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;
import com.rest.RestaurantApp.exceptions.ChangeFinishedStateException;
import com.rest.RestaurantApp.exceptions.IncompatibleEmployeeTypeException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.exceptions.NullArticlesException;
import com.rest.RestaurantApp.exceptions.OrderAlreadyTakenException;
import com.rest.RestaurantApp.exceptions.OrderTakenByWrongEmployeeTypeException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;
import com.rest.RestaurantApp.services.OrderService;


@SpringBootTest
class OrderServiceTest {
	
	@MockBean
	private OrderRepository orderRepository;
	
	@MockBean
	private OrderedArticleRepository orderedArticleRepository;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private ArticleRepository articleRepository;
	
	@MockBean
    SimpMessagingTemplate template;
	
	@Autowired
	private OrderService orderService;
	
	@BeforeEach
	public void setup() {
		Employee waiter = new Employee("petarns99@gmail.com", "Petar", "Markovic", new GregorianCalendar(1999, 10, 29).getTime(), UserType.EMPLOYEE, 1234, EmployeeType.WAITER);
		waiter.setId(1);
		
		Employee waiter1 = new Employee("aleksandar.epic@gmail.com", "Aleksandar", "Cepic", new GregorianCalendar(1999, 6, 19).getTime(), UserType.EMPLOYEE, 2434, EmployeeType.WAITER);
		waiter1.setId(2);
		
		Employee cook = new Employee("matkoman@gmail.com", "Mateja", "Cosovic", new GregorianCalendar(1999, 5, 12).getTime(), UserType.EMPLOYEE, 5436, EmployeeType.COOK);
		cook.setId(3);
		
		Employee barman = new Employee("marko.suljak@gmail.com", "Marko", "Suljak", new GregorianCalendar(1999, 12, 5).getTime(), UserType.EMPLOYEE, 7654, EmployeeType.BARMAN);
		barman.setId(4);
		
		Employee cook1 = new Employee("marko.maric@gmail.com", "Marko", "Maric", new GregorianCalendar(1976, 10, 10).getTime(), UserType.EMPLOYEE, 2468, EmployeeType.COOK);
		cook1.setId(5);
		
		Order order = new Order("Extra chair", 31, LocalDateTime.of(2021, 3, 4, 13, 10, 12), waiter, 40);
		order.setId(1);
		
		Order order1 = new Order("", 22, LocalDateTime.of(2021, 4, 5, 17, 9, 12), waiter1, 50);
		order1.setId(2);
		
		Order order2 = new Order("Join tables", 1, LocalDateTime.of(2021, 12, 13, 17, 0, 2), waiter, 60);
		order2.setId(3);
		
		Order newOrder = new Order("Appetizers first", 5 , LocalDateTime.of(2021, 2, 3, 17, 0, 2), waiter, 70);
		
		Order saveNewOrder = new Order("Appetizers first", 5 , LocalDateTime.of(2021, 2, 3, 17, 0, 2), waiter, 80);
		saveNewOrder.setId(4);
		
		Order orderWithArticles = new Order("", 10, LocalDateTime.of(2021, 3, 6, 13, 10, 12), waiter1, 90);
		orderWithArticles.setId(5);
		
		
		Article article = new Article("Cheeseburger", "Juicy burger", ArticleType.MAIN_COURSE);
		article.setId(1);
		
		Article article1 = new Article("Cheesecake", "Creamy fruit cake", ArticleType.DESSERT);
		article1.setId(2);
		
		OrderedArticle orderedArticle = new OrderedArticle(ArticleStatus.NOT_TAKEN, article);
		orderedArticle.setId(1);
		orderedArticle.setOrder(orderWithArticles);
		
		OrderedArticle changedOrderedArticle = new OrderedArticle(ArticleStatus.TAKEN, article);
		changedOrderedArticle.setId(1);
		changedOrderedArticle.setOrder(orderWithArticles);
		
		OrderedArticle orderedArticle1 = new OrderedArticle(ArticleStatus.PREPARING, article1);
		orderedArticle1.setId(2);
		orderedArticle1.setTakenByEmployee(cook1);
		
		OrderedArticle orderedArticle2 = new OrderedArticle(ArticleStatus.FINISHED, article1);
		orderedArticle2.setId(3);
		orderedArticle2.setTakenByEmployee(cook1);
		
		OrderedArticle newOrderedArticle = new OrderedArticle();
		newOrderedArticle.setArticle(article);
		newOrderedArticle.setOrder(order);
		newOrderedArticle.setTakenByEmployee(waiter);
		newOrderedArticle.setDescription("One plate");
		
		OrderedArticle createdOrderedArticle = new OrderedArticle();
		createdOrderedArticle.setId(4);
		createdOrderedArticle.setArticle(article);
		createdOrderedArticle.setOrder(order);
		
		

		
		
		createdOrderedArticle.setTakenByEmployee(waiter);
		
		createdOrderedArticle.setDescription("One plate");

		
		
		orderWithArticles.setOrderedArticles(Arrays.asList(orderedArticle, orderedArticle1, orderedArticle2));
		
	
		
		given(orderRepository.findById(1)).willReturn(Optional.of(order));
		given(orderRepository.findById(2)).willReturn(Optional.of(order1));
		given(orderRepository.findById(3)).willReturn(Optional.of(order2));
		given(orderRepository.findById(5)).willReturn(Optional.of(orderWithArticles));
		given(orderRepository.findById(6)).willReturn(Optional.empty());
		given(orderRepository.findAll()).willReturn(Arrays.asList(order, order1, order2));
		given(orderRepository.save(newOrder)).willReturn(saveNewOrder);
		given(articleRepository.findById(1)).willReturn(Optional.of(article));
		given(articleRepository.findById(2)).willReturn(Optional.of(article1));
		given(articleRepository.findById(5)).willReturn(Optional.empty());
		given(employeeRepository.findById(1)).willReturn(Optional.of(waiter));
		given(employeeRepository.findById(2)).willReturn(Optional.of(waiter1));
		given(employeeRepository.findById(3)).willReturn(Optional.of(cook));
		given(employeeRepository.findByPincode(5436)).willReturn(Optional.of(cook));
		given(employeeRepository.findByPincode(7654)).willReturn(Optional.of(barman));
		given(employeeRepository.findByPincode(2468)).willReturn(Optional.of(cook1));
		given(orderedArticleRepository.findById(1)).willReturn(Optional.of(orderedArticle));
		given(orderedArticleRepository.findById(2)).willReturn(Optional.of(orderedArticle1));
		given(orderedArticleRepository.findById(3)).willReturn(Optional.of(orderedArticle2));
		given(orderedArticleRepository.findById(4)).willReturn(Optional.of(createdOrderedArticle));
		given(orderedArticleRepository.save(orderedArticle)).willReturn(changedOrderedArticle);
		given(orderedArticleRepository.save(newOrderedArticle)).willReturn(createdOrderedArticle);


		
	}
	

	@Test
	void testFindOne_ValidId() {
		OrderDTO order = orderService.getOne(1);
		
		verify(orderRepository, times(1)).findById(1);
		
		assertEquals(order.getId(), 1);
		assertEquals(order.getDescription(), "Extra chair");
		assertEquals(order.getEmployeeId(), 1);
		assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 3, 4, 13, 10, 12));
		assertEquals(order.getTableNumber(), 31);
	}
	
	@Test
	void testFindOne_InvalidId() {
		assertThrows(NotFoundException.class, () -> {OrderDTO nullOrder = orderService.getOne(6); verify(orderRepository, times(1)).findById(6);});
	}
	
	
	@Test
	void testGetAll() {
		List<OrderDTO> orders = orderService.getAll();
		
		verify(orderRepository, times(1)).findAll();
		
		assertEquals(orders.size(), 3);
	}
	
	@Test
	void testDelete_ValidId() {
		OrderDTO order = orderService.delete(2);
		
		verify(orderRepository, times(1)).findById(2);
		
		assertEquals(order.getId(), 2);
		assertEquals(order.getDescription(), "");
		assertEquals(order.getEmployeeId(), 2);
		assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 4, 5, 17, 9, 12));
		assertEquals(order.getTableNumber(), 22);
		assertTrue(order.isDeleted());
	}
	
	@Test
	void testDelete_InvalidId() {
		assertThrows(NotFoundException.class, () -> {OrderDTO nullOrder = orderService.delete(6); verify(orderRepository, times(1)).findById(6);});
	}
	
	@Test
	void testCreate_ValidOrder() {
		OrderDTO orderToCreate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), Arrays.asList(1), 5, 1, OrderStatus.ACTIVE, 5000);
		OrderDTO createdOrder = orderService.create(orderToCreate);
		
		assertEquals(createdOrder.getId(), 4);
		assertEquals(createdOrder.getDescription(), "Appetizers first");
		assertEquals(createdOrder.getOrderDate(),  LocalDateTime.of(2021, 2, 3, 17, 0, 2));
		assertEquals(createdOrder.getTableNumber(), 5);
		assertEquals(createdOrder.getEmployeeId(), 1);

	}
	
	@Test
	void testCreate_InvalidOrder_NoArticles() {
		OrderDTO orderToCreate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), null, 5, 1, OrderStatus.ACTIVE, 6000);
		assertThrows(NullArticlesException.class, () -> {OrderDTO createdOrder = orderService.create(orderToCreate);});
	}
	
	@Test
	void testCreate_InvalidOrder_OrderTakenByCookOrBarman() {
		OrderDTO orderToCreate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), null, 5, 3, OrderStatus.ACTIVE, 7000);
		assertThrows(OrderTakenByWrongEmployeeTypeException.class, () -> {OrderDTO createdOrder = orderService.create(orderToCreate);});
	}
	
	@Test
	void testUpdate_ValidOrder() {
		OrderDTO orderToUpdate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), Arrays.asList(1), 5, 1, OrderStatus.ACTIVE, 8000);
		OrderDTO updatedOrder = orderService.update(3, orderToUpdate);
		
		assertEquals(updatedOrder.getId(), 4);
		assertEquals(updatedOrder.getDescription(), "Appetizers first");
		assertEquals(updatedOrder.getOrderDate(),  LocalDateTime.of(2021, 2, 3, 17, 0, 2));
		assertEquals(updatedOrder.getTableNumber(), 5);
		assertEquals(updatedOrder.getEmployeeId(), 1);

	}
	
	@Test
	void testUpdate_InvalidOrder_NoArticles() {
		OrderDTO orderToUpdate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), null, 5, 1, OrderStatus.ACTIVE, 9000);
		assertThrows(NotFoundException.class, () -> {OrderDTO updatedOrder = orderService.update(6, orderToUpdate);});

	}
	
	
	@Test
	void testArticlesForOrder_ValidOrder() {
		List<OrderedArticleDTO> orderedArticles = orderService.getArticlesForOrder(5);
		assertEquals(orderedArticles.size(), 3);

	}
	
	@Test
	void testArticlesForOrder_InvalidOrder() {
		assertThrows(NotFoundException.class, () -> {List<OrderedArticleDTO> orderedArticles = orderService.getArticlesForOrder(6);});
	}
	
	@Test
	void testChangeArticleStatus_ValidArticle() {
		OrderedArticleDTO order = orderService.changeStatusOfArticle(5436, 1);
		assertEquals(order.getArticleId(), 1);
		assertEquals(order.getDescription(), null);
		assertEquals(order.getId(), 1);
		assertEquals(order.getOrderId(), 5);
		assertEquals(order.getStatus(), ArticleStatus.TAKEN);
	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_IncompatibleEmployee() {
		assertThrows(IncompatibleEmployeeTypeException.class, () -> {OrderedArticleDTO order = orderService.changeStatusOfArticle(7654, 1);});

	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleAlreadyTaken() {
		assertThrows(OrderAlreadyTakenException.class, () -> {OrderedArticleDTO order = orderService.changeStatusOfArticle(5436, 2);});

	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleStatusIsFinished() {
		assertThrows(ChangeFinishedStateException.class, () -> {OrderedArticleDTO order = orderService.changeStatusOfArticle(2468, 3);});

	}
	
	@Test
	void testCreateArticleForOrder_ValidArticle() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		
		OrderedArticleDTO addedArticle = orderService.createArticleForOrder(orderedArticle.getArticleId(), 1);
		
		assertEquals(addedArticle.getId(), 4);
		assertEquals(addedArticle.getDescription(), "One plate");
		assertEquals(addedArticle.getOrderId(), 1);
		//assertEquals(addedArticle.getStatus(), ArticleStatus.NOT_TAKEN);
	}
	
	@Test
	void testCreateArticleForOrder_InvalidArticle_ArticleNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(5);
		
		assertThrows(NotFoundException.class, () -> {orderService.createArticleForOrder(orderedArticle.getArticleId(), 1);});
	}
	
	@Test
	void testCreateArticleForOrder_InvalidArticle_OrderNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		
		assertThrows(NotFoundException.class, () -> {orderService.createArticleForOrder(orderedArticle.getArticleId(), 6);});
	}
	

	

}
