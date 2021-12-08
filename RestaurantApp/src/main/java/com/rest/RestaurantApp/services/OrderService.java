package com.rest.RestaurantApp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
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

@Service
@Transactional
public class OrderService implements IOrderService {
	
	@Autowired
    SimpMessagingTemplate template;
	
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
			throw new NotFoundException("Order with id " + id + " was not found");
		}
		return new OrderDTO(order.get());
	}

	@Override
	public OrderDTO delete(int id) {
		// TODO Auto-generated method stub
		Optional<Order> orderData = orderRepository.findById(id);
		if (orderData.isEmpty()) {
			throw new NotFoundException("Order with id " + id + " was not found");
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
		// dodati proveru jel sto slobodan
		Employee employee = employeeRepository.findById(order.getEmployeeId()).get();
		
		if(!employee.getEmployeeType().equals(EmployeeType.WAITER)) {
			throw new OrderTakenByWrongEmployeeTypeException("An employee of type " + employee.getEmployeeType().toString() +" can't create a new order");
		}
		
		if(order.getArticles() == null) {
			throw new NullArticlesException("Order must have at least 1 article");
		}
		
		if(order.getArticles().size() == 0) {
			throw new NullArticlesException("Order must have at least 1 article");
		}
		
		List<Article> articles = order.getArticles().stream()
				.map(article -> articleRepository.findById(article).get()).collect(Collectors.toList());
		List<OrderedArticle> orderedArticles = articles.stream()
				.map(orderedArticle -> new OrderedArticle(ArticleStatus.NOT_TAKEN, orderedArticle))
				.collect(Collectors.toList());
		Order createdOrder = new Order(order.getDescription(), order.getTableNumber(), order.getOrderDate(), employee);
		
		for (OrderedArticle o : orderedArticles) {
			createdOrder.addOrderedArticle(o);
		}
		
		Order savedOrder = orderRepository.save(createdOrder);
		notifyCooksAndBarmen(savedOrder);
		return new OrderDTO(savedOrder);
	}
	
	public void notifyCooksAndBarmen(Order order) {
		template.convertAndSend("/orders/new-order", new OrderDTO(order));
	}
	
	@Override
	public OrderDTO update(int id, OrderDTO order) {
		// TODO Auto-generated method stub
		Optional<Order> oldOrderData = orderRepository.findById(id);
		if (oldOrderData.isEmpty()) {
			throw new NotFoundException("Order with id " + id + " was not found");
		}
		if(order.getArticles().size() == 0 || order.getArticles() == null) {
			throw new NullArticlesException("Updated order must have at least 1 article");
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
		  
		  
		List<Article> articles = order.getArticles().stream().map(article -> articleRepository.findById(article).get()).collect(Collectors.toList());
		List<OrderedArticle> orderedArticles = articles.stream().map(orderedArticle -> new OrderedArticle(ArticleStatus.NOT_TAKEN,orderedArticle)).collect(Collectors.toList());
		for (OrderedArticle orderedArticle : orderedArticles) {
			oldOrder.addOrderedArticle(orderedArticle);
		}
		Order updatedOrder = orderRepository.save(oldOrder);
		
		return new OrderDTO(updatedOrder);
	}

	@Override
	public List<OrderedArticleDTO> getArticlesForOrder(int id) {
		// TODO Auto-generated method stub
		Optional<Order> oldOrderData = orderRepository.findById(id);
		if (oldOrderData.isEmpty()) {
			throw new NotFoundException("Order with id " + id + " was not found");
		}
		Order order = oldOrderData.get();
		List<OrderedArticleDTO> orderedArticles = order.getOrderedArticles().stream().map(article -> new OrderedArticleDTO(article)).collect(Collectors.toList());
		return orderedArticles;
	}

	@Override
	public OrderedArticleDTO changeStatusOfArticle(int employeePin, int articleId) {
		// TODO Auto-generated method stub
		OrderedArticle orderedArticle = orderedArticleRepository.findById(articleId).get();
		Employee employee = employeeRepository.findByPincode(employeePin);

		switch(orderedArticle.getStatus()) {
		case NOT_TAKEN:
			if((employee.getEmployeeType().equals(EmployeeType.WAITER)) || (employee.getEmployeeType().equals(EmployeeType.BARMAN) && !orderedArticle.getArticle().getType().equals(ArticleType.DRINK)) || (employee.getEmployeeType().equals(EmployeeType.COOK) && orderedArticle.getArticle().getType().equals(ArticleType.DRINK))) {
				throw new IncompatibleEmployeeTypeException("An employee of type " + employee.getEmployeeType().toString() +" can't take an article that is a type of " + orderedArticle.getArticle().getType());
			}
			orderedArticle.setTakenByEmployee(employee);
			orderedArticle.setStatus(ArticleStatus.TAKEN);
			break;
		case TAKEN:
			if((employee.getEmployeeType().equals(EmployeeType.WAITER)) || (employee.getEmployeeType().equals(EmployeeType.BARMAN) && !orderedArticle.getArticle().getType().equals(ArticleType.DRINK)) || (employee.getEmployeeType().equals(EmployeeType.COOK) && orderedArticle.getArticle().getType().equals(ArticleType.DRINK))) {
				throw new IncompatibleEmployeeTypeException("An employee of type " + employee.getEmployeeType().toString() +" can't start to prepare an article that is a type of " + orderedArticle.getArticle().getType().equals(ArticleType.DRINK));
			}
			if(employee.getId() != orderedArticle.getTakenByEmployee().getId()) {
				throw new OrderAlreadyTakenException("Ordered Article with id " + orderedArticle.getId() + " and name " + orderedArticle.getArticle().getName() + " has already been taken by employee " + orderedArticle.getTakenByEmployee().getName() + " " + orderedArticle.getTakenByEmployee().getSurname());
			}
			
			orderedArticle.setStatus(ArticleStatus.PREPARING);
			notifyWaiters(orderedArticle);
			break;
		case PREPARING:
			if((employee.getEmployeeType().equals(EmployeeType.WAITER)) || (employee.getEmployeeType().equals(EmployeeType.BARMAN) && !orderedArticle.getArticle().getType().equals(ArticleType.DRINK)) || (employee.getEmployeeType().equals(EmployeeType.COOK) && orderedArticle.getArticle().getType().equals(ArticleType.DRINK))) {
				throw new IncompatibleEmployeeTypeException("An employee of type " + employee.getEmployeeType().toString() +" can't start to prepare an article that is a type of " + orderedArticle.getArticle().getType().equals(ArticleType.DRINK));
			}
			if(employee.getId() != orderedArticle.getTakenByEmployee().getId()) {
				throw new OrderAlreadyTakenException("Ordered Article with id " + orderedArticle.getId() + " and name " + orderedArticle.getArticle().getName() + " has already been taken by employee " + orderedArticle.getTakenByEmployee().getName() + " " + orderedArticle.getTakenByEmployee().getSurname());
			}
			
			orderedArticle.setStatus(ArticleStatus.FINISHED);
			notifyWaiters(orderedArticle);
			break;
		default:
			throw new ChangeFinishedStateException("Can't change status of order that is finished");
		}
		
		return new OrderedArticleDTO(orderedArticleRepository.save(orderedArticle));
	}


	public void notifyWaiters(OrderedArticle orderedArticle) {
		template.convertAndSend("/orders/article-status-changed", new OrderedArticleDTO(orderedArticle));
	}

	@Override
	public OrderedArticleDTO createArticleForOrder(OrderedArticleDTO article, int orderId) {
		// TODO Auto-generated method stub
		Optional<Article> articleData = articleRepository.findById(article.getArticleId());
		if(articleData.isEmpty()) {
			throw new NotFoundException("Article with id " + article.getArticleId() + " was not found");
		}
		Article newArticle = articleData.get();
		Optional<Order> orderData = orderRepository.findById(orderId);
		if(orderData.isEmpty()) {
			throw new NotFoundException("Order with id " + orderId + " was not found");
		}
		Order order = orderData.get();
		OrderedArticle orderedArticle = new OrderedArticle(ArticleStatus.NOT_TAKEN, newArticle, order, article.getDescription());
		OrderedArticle savedArticle = orderedArticleRepository.save(orderedArticle);
		notifyCooksAndBarmenArticleCreated(savedArticle);
		return new OrderedArticleDTO(savedArticle);
	}
	
	public void notifyCooksAndBarmenArticleCreated(OrderedArticle orderedArticle) {
		template.convertAndSend("/orders/article-status-changed", "New article" + new OrderedArticleDTO(orderedArticle));
	}
	
	@Override
	public OrderedArticleDTO deleteArticleForOrder(int articleId) {
		// TODO Auto-generated method stub
		Optional<OrderedArticle> orderedArticleData = orderedArticleRepository.findById(articleId);
		if(orderedArticleData.isEmpty()) {
			throw new NotFoundException("Ordered article with id " + articleId + " was not found");
		}
		OrderedArticle orderedArticle = orderedArticleData.get();
		if(!orderedArticle.getStatus().equals(ArticleStatus.NOT_TAKEN)) {
			throw new OrderAlreadyTakenException("Can't delete ordered article with id " + orderedArticle.getId() + " because it is already taken");
		}
		orderedArticle.setDeleted(true);
		notifyCooksAndBarmenArticleDeleted(orderedArticle);
		return new OrderedArticleDTO(orderedArticleRepository.save(orderedArticle));
	}
	
	public void notifyCooksAndBarmenArticleDeleted(OrderedArticle orderedArticle) {
		template.convertAndSend("/orders/article-status-changed", "Deleted article" + new OrderedArticleDTO(orderedArticle));
	}
	
	@Override
	public OrderedArticleDTO updateArticleForOrder(int articleId, OrderedArticleDTO article) {
		// TODO Auto-generated method stub
		Optional<OrderedArticle> orderedArticleData = orderedArticleRepository.findById(articleId);
		if(orderedArticleData == null) {
			return null;
		}
		Optional<Article> articleData = articleRepository.findById(article.getArticleId());
		if(articleData == null) {
			return null;
		}
		OrderedArticle orderedArticle = orderedArticleData.get();
		if(!orderedArticle.getStatus().equals(ArticleStatus.NOT_TAKEN)) {
			throw new OrderAlreadyTakenException("Can't update ordered article with id " + orderedArticle.getId() + " because it is already taken");
		}
		orderedArticle.setDescription(article.getDescription());
		orderedArticle.setArticle(articleRepository.findById(article.getArticleId()).get());
		notifyCooksAndBarmenArticleUpdated(orderedArticle);
		return new OrderedArticleDTO(orderedArticleRepository.save(orderedArticle));
	}
	
	public void notifyCooksAndBarmenArticleUpdated(OrderedArticle orderedArticle) {
		template.convertAndSend("/orders/article-status-changed", "Updated article" + new OrderedArticleDTO(orderedArticle));
	}


}
