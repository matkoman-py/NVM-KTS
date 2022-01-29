package com.rest.RestaurantApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.OrderStatus;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.OrderDTO;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	List<Article> findByType(ArticleType type);
	List<Article> findByNameContainingIgnoreCase(String name);
	List<Article> findByTypeAndNameContainingIgnoreCase(ArticleType type, String name);
	
	@Override
	@Query("select a from Article a where a.isRemoved=false")
	public List<Article> findAll();
}
