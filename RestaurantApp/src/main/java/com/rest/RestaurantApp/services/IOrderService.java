package com.rest.RestaurantApp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.rest.RestaurantApp.dto.ArticlesAndOrderDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.dto.OrderedArticleDTO;


public interface IOrderService {
	
	List<OrderDTO> getAll(); //+++++
	
	OrderDTO getOne(int id); //+++++
	
	
	OrderDTO delete(int id); //+++++
	
	OrderDTO create(OrderDTO order); //+++++
	
	OrderDTO update(int id, OrderDTO order); //+++++
	
	List<OrderedArticleDTO> getArticlesForOrder(int id); //+++++
	
	OrderedArticleDTO changeStatusOfArticle(int employeePin, int articleId); //++++++
	
	OrderedArticleDTO createArticleForOrder(OrderedArticleDTO article, int orderId); //++++
	
	OrderedArticleDTO deleteArticleForOrder(int articleId);
	
	OrderedArticleDTO updateArticleForOrder(int articleId, OrderedArticleDTO article);
	
	OrderDTO addArticlesToOrder(ArticlesAndOrderDTO dto);
}
