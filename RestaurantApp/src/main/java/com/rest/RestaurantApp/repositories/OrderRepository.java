package com.rest.RestaurantApp.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.dto.OrderDTO;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<OrderDTO> findByOrderStatus(OrderStatus orderStatus);
	
    ArrayList<Order> findByEmployeeIdAndOrderStatus(int id, OrderStatus status);


}
