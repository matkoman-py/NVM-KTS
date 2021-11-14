package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
