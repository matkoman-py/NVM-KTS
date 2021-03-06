package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.dto.ArticlesAndOrderDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;
import com.rest.RestaurantApp.dto.OrderedArticleWithDescDTO;
import com.rest.RestaurantApp.exceptions.IncompatibleEmployeeTypeException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.exceptions.OrderAlreadyTakenException;
import com.rest.RestaurantApp.services.OrderService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class OrderControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	void testGetAll() {
		ResponseEntity<OrderDTO[]> responseEntity = restTemplate.withBasicAuth("manager_test", "test")
				.getForEntity("/api/order", OrderDTO[].class);

		OrderDTO[] orders = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orders.length, 13);
	}
	
	@Test
	void testGetOne_ValidId() {
		ResponseEntity<OrderDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test")
				.getForEntity("/api/order/1", OrderDTO.class);

		OrderDTO order = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(order.getArticles().size(), 4);
		assertEquals(order.getDescription(), "No mustard");
		assertEquals(order.getEmployeePin(), 1234);
		assertEquals(order.getTableNumber(), 1);
		assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 1, 3, 12, 43, 33));
	}
	
	@Test
	void testGetOne_InvalidId() {
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test")
				.getForEntity("/api/order/55", String.class);


		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Order with id 55 was not found", responseEntity.getBody());
	}
	
	@Test
	public void testCreateOrder_ValidOrder() throws Exception {
		int size = orderService.getAll().size(); 
		List<OrderedArticleWithDescDTO> list = Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar"));
		OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 1234, OrderStatus.ACTIVE, 0);
		order.setArticlesWithDescription(list);
		ResponseEntity<OrderDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order", order, OrderDTO.class);
		
		OrderDTO createdOrder = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(order.getArticles().size(), 2);
		assertEquals(order.getDescription(), "No honey");
		assertEquals(order.getEmployeePin(), 1234);
		assertEquals(order.getTableNumber(), 3);
		assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 1, 3, 12, 43, 33));
		
		List<OrderDTO> orders = orderService.getAll();
		assertEquals(size + 1, orders.size()); 

		assertEquals(order.getArticles().size(), orders.get(orders.size() - 1).getArticles().size());
		assertEquals(order.getDescription(), orders.get(orders.size() - 1).getDescription());
		assertEquals(order.getEmployeePin(), orders.get(orders.size() - 1).getEmployeePin());
		assertEquals(order.getTableNumber(), orders.get(orders.size() - 1).getTableNumber());
		assertEquals(order.getOrderDate(), orders.get(orders.size() - 1).getOrderDate());
		orderService.delete(size+1);
	}
	
	@Test
	public void testCreateOrder_InvalidOrder_NoArticles() throws Exception {
		int size = orderService.getAll().size(); 
		OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), new ArrayList<Integer>(), 3, 1234, OrderStatus.ACTIVE, 0);
		
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order", order, String.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Order must have at least 1 article");
		
		List<OrderDTO> orders = orderService.getAll();
		assertEquals(size, orders.size()); 
	}
	
	@Test
	public void testCreateOrder_InvalidOrder_OrderTakenByCookOrBarman() throws Exception {
		int size = orderService.getAll().size(); 
		OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 2, 3, 12, 43, 33), Arrays.asList(1, 2), 4, 4322, OrderStatus.ACTIVE, 0);
		
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order", order, String.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "An employee of type BARMAN can't create a new order");
		
		List<OrderDTO> orders = orderService.getAll();
		assertEquals(size, orders.size()); 
	}
	
	@Test
	void testUpdate_ValidOrder() {
		int size = orderService.getAll().size(); 
		OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 1234, OrderStatus.ACTIVE, 0);
		
		ResponseEntity<OrderDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/2", HttpMethod.PUT, new HttpEntity<OrderDTO>(order), OrderDTO.class);
		
		OrderDTO updatedOrder = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedOrder.getArticles().size(), 2);
		assertEquals(updatedOrder.getDescription(), "No honey");
		assertEquals(updatedOrder.getEmployeePin(), 1234);
		assertEquals(updatedOrder.getTableNumber(), 3);
		assertEquals(updatedOrder.getOrderDate(), LocalDateTime.of(2021, 1, 3, 12, 43, 33));
		assertEquals(updatedOrder.getId(), 2);
		
		List<OrderDTO> orders = orderService.getAll();
		assertEquals(size, orders.size()); 

	}
	
	@Test
	void testUpdate_InvalidOrder_NoArticles() {
		int size = orderService.getAll().size(); 
		OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), new ArrayList<Integer>(), 3, 3, OrderStatus.ACTIVE, 0);
		
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/2", HttpMethod.PUT, new HttpEntity<OrderDTO>(order), String.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		assertEquals(responseEntity.getBody(), "Updated order must have at least 1 article");
		
		List<OrderDTO> orders = orderService.getAll();
		assertEquals(size, orders.size()); 

	}
	
	@Test
	public void testDelete_ValidId() throws Exception {
		List<OrderedArticleWithDescDTO> list = Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar"));
		OrderDTO orderDTO = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 1234, OrderStatus.ACTIVE, 0);
		orderDTO.setArticlesWithDescription(list);
		OrderDTO order = orderService.create(orderDTO);
		
		int size = orderService.getAll().size();
		ResponseEntity<Void> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/" + order.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null),
				Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(size - 1, orderService.getAll().size());
	}
	
	@Test
	public void testDelete_InvalidId() throws Exception {
		
		int size = orderService.getAll().size();
		ResponseEntity<Void> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/55", HttpMethod.DELETE, new HttpEntity<Object>(null),
				Void.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		assertEquals(size, orderService.getAll().size());
	}
	
	
	
	@Test
	void testArticlesForOrder_ValidOrder() {
		ResponseEntity<OrderedArticleDTO[]> responseEntity = restTemplate.withBasicAuth("manager_test", "test")
				.getForEntity("/api/order/articles/1", OrderedArticleDTO[].class);

		OrderedArticleDTO[] articles = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(articles.length, 4);

	}
	
	@Test
	void testArticlesForOrder_InvalidOrder() {
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test")
				.getForEntity("/api/order/articles/33", String.class);


		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Order with id 33 was not found");
	}
	
	@Test
	void testChangeArticleStatus_ValidArticle() {
		
		ResponseEntity<OrderedArticleDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/article/13/4269", HttpMethod.PUT, null, OrderedArticleDTO.class);
		
		OrderedArticleDTO updatedOrder = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedOrder.getStatus(), ArticleStatus.TAKEN);
		assertEquals(updatedOrder.getArticleId(), 2);
		assertEquals(updatedOrder.getOrderId(), 1);
		assertEquals(updatedOrder.getDescription(), "bez buta");
	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_IncompatibleEmployee() {
		
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/article/13/4322", HttpMethod.PUT, null, String.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "An employee of type BARMAN can't take an article that is a type of MAIN_COURSE");

	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleAlreadyTaken() {

		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/article/7/2910", HttpMethod.PUT, null, String.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Ordered Article with id 7 and name Mesano meso has already been taken by employee Petar Petrovic");

	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleFinished() {

		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/article/3/4269", HttpMethod.PUT, null, String.class);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Can't change status of order that is finished");

	}
	
	//addArticle/{id}
	@Test
	public void testCreateArticleForOrder_ValidArticle() throws Exception {
		int size = orderService.getArticlesForOrder(8).size();
		//OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 3);
		OrderedArticleDTO article = new OrderedArticleDTO(1, ArticleStatus.NOT_TAKEN, 8, "");
		ResponseEntity<OrderedArticleDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order/addArticle/8", article, OrderedArticleDTO.class);
		
		OrderedArticleDTO orderedArticle = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(orderedArticle.getArticleId(), 1);
		assertEquals(orderedArticle.getDescription(), "");
		assertEquals(orderedArticle.getStatus(), ArticleStatus.NOT_TAKEN);
		
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(8);
		assertEquals(size + 1, articles.size()); 

	}
	
	@Test
	public void testCreateArticleForOrder_InvalidArticle_ArticleNotFound() throws Exception {
		int size = orderService.getArticlesForOrder(8).size();
		//OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 3);
		OrderedArticleDTO article = new OrderedArticleDTO(53, ArticleStatus.NOT_TAKEN, 8, "");
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order/addArticle/8", article, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Article with id 53 was not found");
		
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(8);
		assertEquals(size, articles.size()); 


	}
	
	@Test
	public void testCreateArticleForOrder_InvalidArticle_OrderNotFound() throws Exception {
		int size = orderService.getArticlesForOrder(8).size();
		//OrderDTO order = new OrderDTO(false, "No honey", LocalDateTime.of(2021, 1, 3, 12, 43, 33), Arrays.asList(1,2), 3, 3);
		OrderedArticleDTO article = new OrderedArticleDTO(1, ArticleStatus.NOT_TAKEN, 54, "");
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").postForEntity("/api/order/addArticle/54", article, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Order with id 54 was not found");
		
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(8);
		assertEquals(size, articles.size()); 
	}
	
	@Test
	public void testDeleteArticleForOrder_ValidId() throws Exception {
		OrderedArticleDTO article = new OrderedArticleDTO(1, ArticleStatus.NOT_TAKEN, 8, "");
		OrderedArticleDTO createdArticle = orderService.createArticleForOrder(article, 8);
		int size = orderService.getArticlesForOrder(8).size();
		//removeArticle/{id}
		ResponseEntity<OrderedArticleDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/removeArticle/" + createdArticle.getId(), HttpMethod.DELETE, null,
				OrderedArticleDTO.class);
		
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(8);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size-1, articles.size());
	}
	
	@Test
	public void testDeleteArticleForOrder_InvalidId_ArticleNotFound() throws Exception {
		//removeArticle/{id}
		int size = orderService.getArticlesForOrder(8).size();
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/removeArticle/59", HttpMethod.DELETE, null,
				String.class);
		
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(8);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Ordered article with id 59 was not found");
		assertEquals(size, articles.size());
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_InvalidArticle_ArticleNotFound() {
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(66,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 1);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/add-articles-to-order", HttpMethod.PUT, new HttpEntity<ArticlesAndOrderDTO>(dto), String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Article with id 66 was not found");
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_InvalidArticle_OrderNotFound() {
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 141);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/add-articles-to-order", HttpMethod.PUT, new HttpEntity<ArticlesAndOrderDTO>(dto), String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), "Order with id 141 was not found");
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_ValidArticles() {
		int size =  orderService.getArticlesForOrder(1).size();
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 1);
		ResponseEntity<OrderDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/order/add-articles-to-order", HttpMethod.PUT, new HttpEntity<ArticlesAndOrderDTO>(dto), OrderDTO.class);
		int newSize =  orderService.getArticlesForOrder(1).size();
		assertEquals(size+2, newSize);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
	}

}
