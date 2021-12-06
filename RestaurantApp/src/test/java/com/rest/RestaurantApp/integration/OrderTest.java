package com.rest.RestaurantApp.integration;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.services.OrderService;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

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
		assertEquals(1, categories.length);
		
	}
	

}
