package com.rest.RestaurantApp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderRepository;


@Service
@Transactional
public class OrderService implements IOrderService {
	
	private OrderRepository orderRepository;
	
	private EmployeeRepository employeeRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository) {
		this.orderRepository = orderRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<OrderDTO> getAll() {
		// TODO Auto-generated method stub
		List<OrderDTO> orders = orderRepository.findAll().stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
		return orders;
		
	}

	@Override
	public OrderDTO getOne(int id) {
		// TODO Auto-generated method stub
		Optional<Order> order = orderRepository.findById(id);
		if(order.isEmpty()) {
			return null;
		}
		return new OrderDTO(order.get());
	}

	@Override
	public OrderDTO delete(int id) {
		// TODO Auto-generated method stub
		Optional<Order> orderData = orderRepository.findById(id);
		if (orderData.isEmpty()) {
			return null;
		}
		Order order = orderData.get();
		order.setDeleted(true);
		orderRepository.save(order);
		return new OrderDTO(order);
		
	}

	@Override
	public OrderDTO create(OrderDTO order) {
		// TODO Auto-generated method stub
		Order createdOrder = new Order(order.getDescription(), order.getTableNumber(), order.getOrderDate(), employeeRepository.getById(order.getEmployeeId()));
		Order savedOrder = orderRepository.save(createdOrder);
		return new OrderDTO(savedOrder);
	}

	@Override
	public OrderDTO update(int id, OrderDTO order) {
		// TODO Auto-generated method stub
		Optional<Order> oldOrderData = orderRepository.findById(id);
		if(oldOrderData.isEmpty()) {
			return null;
		}
		Order oldOrder = oldOrderData.get();
		oldOrder.setDescription(order.getDescription());
		oldOrder.setEmployee(employeeRepository.findById(order.getEmployeeId()).get());
		oldOrder.setOrderDate(order.getOrderDate());
		oldOrder.setTableNumber(order.getTableNumber());
		Order updatedOrder = orderRepository.save(oldOrder);
		return new OrderDTO(updatedOrder);
	}

}
