package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.OrderedArticle;

public interface OrderedArticleRepository extends JpaRepository<OrderedArticle, Integer> {

}
