package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
