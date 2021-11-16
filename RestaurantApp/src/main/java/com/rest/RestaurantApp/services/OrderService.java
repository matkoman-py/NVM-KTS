package com.rest.RestaurantApp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.OrderRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;

@Service
@Transactional
public class OrderService implements IOrderService {

	private OrderRepository orderRepository;

	private EmployeeRepository employeeRepository;

	private OrderedArticleRepository orderedArticleRepository;

	private ArticleRepository articleRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository,
			OrderedArticleRepository orderedArticleRepository, ArticleRepository articleRepository) {
		this.orderRepository = orderRepository;
		this.employeeRepository = employeeRepository;
		this.orderedArticleRepository = orderedArticleRepository;
		this.articleRepository = articleRepository;
	}

	@Override
	public List<OrderDTO> getAll() {
		// TODO Auto-generated method stub
		List<OrderDTO> orders = orderRepository.findAll().stream().map(order -> new OrderDTO(order))
				.collect(Collectors.toList());
		return orders;

	}

	@Override
	public OrderDTO getOne(int id) {
		// TODO Auto-generated method stub
		Optional<Order> order = orderRepository.findById(id);
		if (order.isEmpty()) {
			return null;
		}
		System.out.println("DASIKDL:ASKJD:LASKD:LSAK" + order.get().getOrderedArticles().size());
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
		for (OrderedArticle orderedArticle : order.getOrderedArticles()) {
			order.removeOrderedArticle(orderedArticle);
		}
		order.getOrderedArticles().clear();
		orderRepository.save(order);
		return new OrderDTO(order);

	}

	@Override
	public OrderDTO create(OrderDTO order) {
		// TODO Auto-generated method stub
		// List<Article> articles = order.getOrderedArticles().stream().map(article ->
		// article)
		Employee employee = employeeRepository.getById(order.getEmployeeId());
		List<Article> articles = order.getOrderedArticles().stream()
				.map(article -> articleRepository.findById(article).get()).collect(Collectors.toList());
		List<OrderedArticle> orderedArticles = articles.stream()
				.map(orderedArticle -> new OrderedArticle(ArticleStatus.NOT_TAKEN, orderedArticle))
				.collect(Collectors.toList());
		Order createdOrder = new Order(order.getDescription(), order.getTableNumber(), order.getOrderDate(), employee);
		for (OrderedArticle o : orderedArticles) {
			createdOrder.addOrderedArticle(o);
		}
		Order savedOrder = orderRepository.save(createdOrder);
		return new OrderDTO(savedOrder);
	}

	@Override
	public OrderDTO update(int id, OrderDTO order) {
		// TODO Auto-generated method stub
		Optional<Order> oldOrderData = orderRepository.findById(id);
		if (oldOrderData.isEmpty()) {
			return null;
		}
		
		Order oldOrder = oldOrderData.get();
		for (OrderedArticle orderedArticle : oldOrder.getOrderedArticles()) {
			oldOrder.removeOrderedArticle(orderedArticle);
		}
		oldOrder.getOrderedArticles().clear();
		oldOrder.setDescription(order.getDescription());
		oldOrder.setEmployee(employeeRepository.findById(order.getEmployeeId()).get());
		oldOrder.setOrderDate(order.getOrderDate());
		oldOrder.setTableNumber(order.getTableNumber());
		  
		  
		List<Article> articles = order.getOrderedArticles().stream().map(article -> articleRepository.findById(article).get()).collect(Collectors.toList());
		List<OrderedArticle> orderedArticles = articles.stream().map(orderedArticle -> new OrderedArticle(ArticleStatus.NOT_TAKEN,orderedArticle)).collect(Collectors.toList());
		for (OrderedArticle orderedArticle : orderedArticles) {
			oldOrder.addOrderedArticle(orderedArticle);
		}
		Order updatedOrder = orderRepository.save(oldOrder);
		
		return new OrderDTO(updatedOrder);
	}



}
