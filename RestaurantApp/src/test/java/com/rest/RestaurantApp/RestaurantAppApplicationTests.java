package com.rest.RestaurantApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.repositories.OrderRepository;
import com.rest.RestaurantApp.services.OrderService;

@SpringBootTest
class RestaurantAppApplicationTests {

	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderService orderService;
	
	@BeforeEach
	void setMockOutput() {
		Employee employee = new Employee("dsadas", null, null, null, null, 0, null);
		employee.setId(1);
		Order order = new Order("blabla", 69, null, employee);
		Optional<Order> orderData = Optional.of(order);
		when(orderRepository.findById(1)).thenReturn(orderData);
	}
	
	@Test
	void contextLoads() {
		assertEquals(orderService.getOne(1).getDescription(), "blabla");
	}

}
