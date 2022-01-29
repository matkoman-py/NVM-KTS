package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleStatus;

import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;

public interface OrderedArticleRepository extends JpaRepository<OrderedArticle, Integer> {

    @Query("select oa from OrderedArticle oa join fetch oa.order oao")
    ArrayList<OrderedArticle> findAllWithOrder();
    
    ArrayList<OrderedArticle> findByTakenByEmployeeIdAndStatusNot(int id, ArticleStatus status);
    
    ArrayList<OrderedArticle> findByArticleIdAndStatusNot(int id, ArticleStatus status);

}
