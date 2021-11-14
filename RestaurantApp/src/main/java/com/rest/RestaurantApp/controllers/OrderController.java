package com.rest.RestaurantApp.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.services.OrderService;




@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OrderDTO>> getAll() {
		return ResponseEntity.ok(orderService.getAll());
	}
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> getOne(@PathVariable("id") int id) {
		OrderDTO order = orderService.getOne(id);
		if(order == null) {
			return new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		OrderDTO order = orderService.delete(id);
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("order with id " + id + " deleted successfully", HttpStatus.OK);

	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order) {
		OrderDTO createdOrder = orderService.create(order);
		return new ResponseEntity<OrderDTO>(createdOrder, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderDTO> update(@PathVariable("id") int id, @RequestBody OrderDTO order) {
		OrderDTO updateOrder = orderService.update(id, order);
		if(updateOrder == null) {
			return new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDTO>(updateOrder, HttpStatus.OK);
	}
	

}
