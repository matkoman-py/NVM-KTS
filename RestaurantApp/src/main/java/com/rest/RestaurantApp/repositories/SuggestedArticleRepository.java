package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.SuggestedArticle;

public interface SuggestedArticleRepository extends JpaRepository<SuggestedArticle, Integer>{

}
