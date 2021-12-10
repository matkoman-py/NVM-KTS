package com.rest.RestaurantApp.integration;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.services.EmployeeService;
import com.rest.RestaurantApp.services.OrderService;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderTest {
	
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	
	@Autowired
	private OrderService orderService;

	
	@Test
	public void test() throws Exception {
		ResponseEntity<OrderDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/order", OrderDTO[].class);

		OrderDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(13, categories.length);
		
	}
	
	@Test
	public void testGetOne_ValidId() {
		ResponseEntity<OrderDTO> responseEntity = restTemplate
				.getForEntity("/api/order/1", OrderDTO.class);

		OrderDTO order = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(order.getArticles().size(), 4);
		assertEquals(order.getDescription(), "No mustard");
		assertEquals(order.getEmployeeId(), 3);
		assertEquals(order.getTableNumber(), 1);
		assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 1, 3, 12, 43, 33));
	}
	

}
