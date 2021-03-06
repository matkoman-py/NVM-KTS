package com.rest.RestaurantApp.integration;

import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.ArticlesAndOrderDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;
import com.rest.RestaurantApp.dto.OrderedArticleWithDescDTO;
import com.rest.RestaurantApp.exceptions.ChangeFinishedStateException;
import com.rest.RestaurantApp.exceptions.IncompatibleEmployeeTypeException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.exceptions.NullArticlesException;
import com.rest.RestaurantApp.exceptions.OrderAlreadyTakenException;
import com.rest.RestaurantApp.exceptions.OrderTakenByWrongEmployeeTypeException;
import com.rest.RestaurantApp.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class OrderServiceTest {
	
	@Autowired
    private OrderService orderService;

	@Test
    public void testGetAll() {
        List<OrderDTO> orders = orderService.getAll();

        assertEquals(13, orders.size());
    }
	
	@Test
    public void testGetAllActive() {
        List<OrderDTO> orders = orderService.getAllActive();

        assertEquals(13, orders.size());
    }
	
	@Test
    public void testFindOne_ValidId() {
        OrderDTO order = orderService.getOne(1);

        assertEquals((int) order.getId(), 1);
        assertEquals(order.getDescription(), "No mustard");
        assertEquals(order.getOrderDate(), LocalDateTime.of(2021, 1, 3, 12, 43,33));
        assertEquals(order.getTableNumber(), 1);
        assertEquals(order.getEmployeePin(), 1234);
    }
	
	@Test
    public void testFindOne_InvalidId() {
        assertThrows(NotFoundException.class, () -> {
            orderService.getOne(26);
        });
    }
	
	@Test
    public void testDelete_ValidId() {
		OrderDTO orderDTO = new OrderDTO(false, "Nothing", LocalDateTime.of(2021, 1,1,13,13,13), Arrays.asList(1,2,2), 3, 1234, OrderStatus.ACTIVE, 4000);
		List<OrderedArticleWithDescDTO> list = Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar"));
		orderDTO.setArticlesWithDescription(list);
        OrderDTO createdOrder = orderService.create(orderDTO);
        int oldSize = orderService.getAll().size();
        OrderDTO order = orderService.delete(createdOrder.getId());

        List<OrderDTO> orders = orderService.getAll();
        int newSize = orders.size();

        assertEquals(oldSize - 1, newSize);
        assertEquals((int) order.getId(), order.getId());
        assertThrows(NoSuchElementException.class, () -> orders.stream().filter(i -> i.getId() == createdOrder.getId())
                .findFirst().get());
    }
	
	@Test
    public void testDelete_InvalidId() {
        assertThrows(NotFoundException.class, () -> {
            orderService.delete(27);
        });
    }
	
	@Test
    public void testCreate_ValidOrder() {
        int oldSize = orderService.getAll().size();
        OrderDTO orderDTO = new OrderDTO(false, "", LocalDateTime.of(2021, 1,1,1,1,1), Arrays.asList(1,3), 3, 1234, OrderStatus.ACTIVE, 4000);
        
        List<OrderedArticleWithDescDTO> list = Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar"));
		orderDTO.setArticlesWithDescription(list);
        OrderDTO created = orderService.create(orderDTO);
        List<OrderDTO> orders = orderService.getAll();
        int newSize = orders.size();

        orderService.delete(created.getId());

        assertEquals((int) created.getId(), 14);
        assertEquals(created.getOrderDate(), LocalDateTime.of(2021, 1, 1, 1, 1,1));
        assertEquals(created.getTableNumber(), 3);
        assertEquals(created.getEmployeePin(), 1234);
        assertEquals(oldSize + 1, newSize);
    }
	
	@Test
	void testCreate_InvalidOrder_NoArticles() {
		OrderDTO orderToCreate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), null, 3, 1234, OrderStatus.ACTIVE, 5000);
		assertThrows(NullArticlesException.class, () -> {orderService.create(orderToCreate);});
	}
	
	@Test
	void testCreate_InvalidOrder_OrderTakenByCookOrBarman() {
		OrderDTO orderToCreate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), Arrays.asList(1), 5, 4322, OrderStatus.ACTIVE, 5000);
		assertThrows(OrderTakenByWrongEmployeeTypeException.class, () -> {orderService.create(orderToCreate);});
	}
	
	@Test
	void testUpdate_ValidOrder() {
		int oldSize = orderService.getAll().size();
        OrderDTO orderDTO = new OrderDTO(false, "", LocalDateTime.of(2021, 1,1,1,1,1), Arrays.asList(1,3), 3, 1234, OrderStatus.ACTIVE, 5041);

        OrderDTO updated = orderService.update(2, orderDTO);
        List<OrderDTO> orders = orderService.getAll();
        int newSize = orders.size();

        assertEquals((int) updated.getId(), 2);
        assertEquals(updated.getOrderDate(), LocalDateTime.of(2021, 1, 1, 1, 1,1));
        assertEquals(updated.getTableNumber(), 3);
        assertEquals(updated.getEmployeePin(), 1234);
        assertEquals(oldSize, newSize);
	}
	
	@Test
	void testUpdate_InvalidOrder_NoArticles() {
		OrderDTO orderToUpdate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), new ArrayList<Integer>(), 3, 3, OrderStatus.ACTIVE, 10002);
		assertThrows(NullArticlesException.class, () -> {orderService.update(7, orderToUpdate);});
	}
	
	@Test
	void testUpdate_InvalidOrder_OrderNotFound() {
		OrderDTO orderToUpdate = new OrderDTO(false, "Appetizers first", LocalDateTime.of(2021, 2, 3, 17, 0, 2), new ArrayList<Integer>(), 3, 3, OrderStatus.ACTIVE, 10001);
		assertThrows(NotFoundException.class, () -> {orderService.update(55, orderToUpdate);});
	}
	
	@Test
	void testArticlesForOrder_ValidOrder() {
		List<OrderedArticleDTO> orderedArticles = orderService.getArticlesForOrder(1);
		assertEquals(orderedArticles.size(), 4);
	}
	
	@Test
	void testArticlesForOrder_InvalidOrder() {
		assertThrows(NotFoundException.class, () -> {orderService.getArticlesForOrder(53);});
	}
	
	@Test
	void testChangeArticleStatus_ValidArticle() {
		OrderedArticleDTO order = orderService.changeStatusOfArticle(4269, 14);
		assertEquals(order.getArticleId(), 1);
		assertEquals(order.getDescription(), null);
		assertEquals(order.getOrderId(), 7);
		assertEquals(order.getStatus(), ArticleStatus.TAKEN);
	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_IncompatibleEmployee() {
		assertThrows(IncompatibleEmployeeTypeException.class, () -> {orderService.changeStatusOfArticle(1234, 14);});
	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleAlreadyTaken() {
		assertThrows(OrderAlreadyTakenException.class, () -> {orderService.changeStatusOfArticle(2910, 7);});

	}
	
	@Test
	void testChangeArticleStatus_InvalidArticle_ArticleStatusIsFinished() {
		assertThrows(ChangeFinishedStateException.class, () -> {orderService.changeStatusOfArticle(4269, 3);});
	}
	
	@Test
	void testCreateArticleForOrder_ValidArticle() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		int size = orderService.getArticlesForOrder(1).size();
		OrderedArticleDTO addedArticle = orderService.createArticleForOrder(orderedArticle, 1);
		int newSize = orderService.getArticlesForOrder(1).size();
		orderService.deleteArticleForOrder(addedArticle.getId());
		assertEquals(addedArticle.getDescription(), "One plate");
		assertEquals(addedArticle.getOrderId(), 1);
		assertEquals(addedArticle.getStatus(), ArticleStatus.NOT_TAKEN);
		assertEquals(size+1, newSize);
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_ValidArticles() {
		int size =  orderService.getArticlesForOrder(1).size();
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 1);
		orderService.addArticlesToOrder(dto);
		int newSize =  orderService.getArticlesForOrder(1).size();
		assertEquals(size+2, newSize);	
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_InvalidArticle_ArticleNotFound() {
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(66,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 1);
		assertThrows(NotFoundException.class, () -> {orderService.addArticlesToOrder(dto);});
	}
	
	@Test
	void testCreateArticlesMultipleForOrder_InvalidArticle_OrderNotFound() {
		ArticlesAndOrderDTO dto = new ArticlesAndOrderDTO(Arrays.asList(new OrderedArticleWithDescDTO(1,"dobar"), new OrderedArticleWithDescDTO(2,"dobar")), 141);
		assertThrows(NotFoundException.class, () -> {orderService.addArticlesToOrder(dto);});
	}
	
	@Test
	void testCreateArticleForOrder_InvalidArticle_ArticleNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(65);
		
		assertThrows(NotFoundException.class, () -> {orderService.createArticleForOrder(orderedArticle, 1);});
	}
	
	@Test
	void testCreateArticleForOrder_InvalidArticle_OrderNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		
		assertThrows(NotFoundException.class, () -> {orderService.createArticleForOrder(orderedArticle, 75);});
	}
	
	@Test
	void testUpdateArticleForOrder_ValidArticle() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		int size = orderService.getArticlesForOrder(1).size();
		OrderedArticleDTO updated = orderService.updateArticleForOrder(13, orderedArticle);
		int newSize = orderService.getArticlesForOrder(1).size();
		assertEquals(updated.getId(), 13);
		assertEquals(updated.getDescription(), "One plate");
		assertEquals(updated.getOrderId(), 1);
		assertEquals(size, newSize);
	}
	
	@Test
	void testUpdateArticleForOrder_InvalidArticle_OrderedArticleNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		
		assertThrows(NotFoundException.class, () -> {orderService.updateArticleForOrder(75, orderedArticle);});
	}
	
	@Test
	void testUpdateArticleForOrder_InvalidArticle_ArticleNotFound() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(45);
		
		assertThrows(NotFoundException.class, () -> {orderService.updateArticleForOrder(1, orderedArticle);});
	}
	
	@Test
	void testUpdateArticleForOrder_InvalidArticle_OrderAlreadyTaken() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		
		assertThrows(OrderAlreadyTakenException.class, () -> {orderService.updateArticleForOrder(1, orderedArticle);});
	}
	
	@Test
	void testDeleteArticleForOrder_InvalidArticle_OrderedArticleNotFound() {
		assertThrows(NotFoundException.class, () -> {orderService.deleteArticleForOrder(42);});
	}
	
	
	@Test
	void testDeleteArticleForOrder_InvalidArticle_OrderAlreadyTaken() {
		assertThrows(OrderAlreadyTakenException.class, () -> {orderService.deleteArticleForOrder(1);});
	}
	
	@Test
	void testDeleteArticleForOrder_ValidArticle() {
		OrderedArticleDTO orderedArticle = new OrderedArticleDTO();
		orderedArticle.setDescription("One plate");
		orderedArticle.setArticleId(1);
		OrderedArticleDTO addedArticle = orderService.createArticleForOrder(orderedArticle, 1);
		int size = orderService.getArticlesForOrder(1).size();
		orderService.deleteArticleForOrder(addedArticle.getId());
		List<OrderedArticleDTO> articles = orderService.getArticlesForOrder(1);
		int newSize = articles.size();
		assertEquals(size-1, newSize);
		assertThrows(NoSuchElementException.class, () -> articles.stream().filter(i -> i.getId() == addedArticle.getId())
                .findFirst().get());
	}

}
