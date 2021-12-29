package com.rest.RestaurantApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.enums.ArticleType;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	List<Article> findByType(ArticleType type);
	List<Article> findByNameContainingIgnoreCase(String name);
	List<Article> findByTypeAndNameContainingIgnoreCase(ArticleType type, String name);
}
