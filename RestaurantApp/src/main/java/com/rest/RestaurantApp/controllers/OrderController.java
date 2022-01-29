package com.rest.RestaurantApp.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.ArticlesAndOrderDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;
import com.rest.RestaurantApp.exceptions.ChangeFinishedStateException;
import com.rest.RestaurantApp.exceptions.IncompatibleEmployeeTypeException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.exceptions.NullArticlesException;
import com.rest.RestaurantApp.exceptions.OrderAlreadyTakenException;
import com.rest.RestaurantApp.exceptions.OrderTakenByWrongEmployeeTypeException;
import com.rest.RestaurantApp.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAll() {
		return ResponseEntity.ok(orderService.getAll());
	}
	
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAllActive() {
		return ResponseEntity.ok(orderService.getAllActive());
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> getOne(@PathVariable("id") int id) {
		OrderDTO order = orderService.getOne(id);
		if(order == null) {
			return new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderedArticleDTO>> getArticlesForOrder(@PathVariable("id") int id) {
		List<OrderedArticleDTO> orderedArticles = orderService.getArticlesForOrder(id);
		return new ResponseEntity<List<OrderedArticleDTO>>(orderedArticles, HttpStatus.OK);	
	}
	
	@ExceptionHandler(value = NullArticlesException.class)
	public ResponseEntity handleNullArticlesException(NullArticlesException nullArticlesException) {
        return new ResponseEntity(nullArticlesException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = OrderTakenByWrongEmployeeTypeException.class)
	public ResponseEntity handleOrderTakenByWrongEmployeeTypeException(OrderTakenByWrongEmployeeTypeException orderTakenByWrongEmployeeTypeException) {
        return new ResponseEntity(orderTakenByWrongEmployeeTypeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = OrderAlreadyTakenException.class)
	public ResponseEntity handleOrderAlreadyTakenException(OrderAlreadyTakenException orderAlreadyTakenException) {
        return new ResponseEntity(orderAlreadyTakenException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = IncompatibleEmployeeTypeException.class)
	public ResponseEntity handleIncompatibleEmployeeTypeException(IncompatibleEmployeeTypeException incompatibleEmployeeTypeException) {
        return new ResponseEntity(incompatibleEmployeeTypeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = ChangeFinishedStateException.class)
	public ResponseEntity handleChangeFinishedStateException(ChangeFinishedStateException changeFinishedStateException) {
        return new ResponseEntity(changeFinishedStateException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		OrderDTO order = orderService.delete(id);
		return new ResponseEntity<>("order with id " + id + " deleted successfully", HttpStatus.OK);

	}
	@PreAuthorize("hasAuthority('WAITER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order) {
		OrderDTO createdOrder = orderService.create(order);
		return new ResponseEntity<OrderDTO>(createdOrder, HttpStatus.CREATED);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> update(@PathVariable("id") int id, @RequestBody OrderDTO order) {
		OrderDTO updateOrder = orderService.update(id, order);
		return new ResponseEntity<OrderDTO>(updateOrder, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@PutMapping(value = "article/{id}/{pin}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderedArticleDTO> changeArticleStatus(@PathVariable("id") int id, @PathVariable("pin") int pin) {
		OrderedArticleDTO article = orderService.changeStatusOfArticle(pin, id);
		return new ResponseEntity<OrderedArticleDTO>(article, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@PutMapping(value = "/add-articles-to-order", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> addArticlesToDTO(@RequestBody ArticlesAndOrderDTO dto) {
		OrderDTO article = orderService.addArticlesToOrder(dto);
		return new ResponseEntity<OrderDTO>(article, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@PostMapping(value = "addArticle/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderedArticleDTO> addArticle(@RequestBody OrderedArticleDTO article, @PathVariable("id") int orderId) {
		OrderedArticleDTO orderedArticle = orderService.createArticleForOrder(article, orderId);
		return new ResponseEntity<OrderedArticleDTO>(orderedArticle, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@DeleteMapping(value = "removeArticle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderedArticleDTO> removeArticle(@PathVariable("id") int articleId) {
		OrderedArticleDTO orderedArticle = orderService.deleteArticleForOrder(articleId);
		return new ResponseEntity<OrderedArticleDTO>(orderedArticle, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@PutMapping(value = "updateArticle/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderedArticleDTO> updateArticle(@RequestBody OrderedArticleDTO article, @PathVariable("id") int orderId) {
		OrderedArticleDTO orderedArticle = orderService.updateArticleForOrder(orderId, article);
		return new ResponseEntity<OrderedArticleDTO>(orderedArticle, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "updateOrderStatus/{id}/{orderStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable("id") int id, @PathVariable("orderStatus") String orderStatus){
		OrderDTO order = orderService.updateOrderStatus(id, OrderStatus.valueOf(orderStatus));
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> search(@RequestParam(value = "orderStatus", required = false, defaultValue = "") String orderStatus) {
		return ResponseEntity.ok(orderService.search(OrderStatus.valueOf(orderStatus)));	
	}
	@PreAuthorize("hasAuthority('WAITER') or hasAuthority('BARMAN') or hasAuthority('COOK')")
	@GetMapping(value = "articles/search/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderedArticleDTO>> searchArticles(@PathVariable("id") int id, 
			@RequestParam(value = "articleStatus", required = false, defaultValue = "") String articleStatus) {
		return ResponseEntity.ok(orderService.searchArticles(id, ArticleStatus.valueOf(articleStatus)));	
	}
	
	//finish order
	//proversis pin 
	//stavis order status na finished
	
}
