package com.rest.RestaurantApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Menu;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.MenuDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.MenuRepository;

@Service
@Transactional
public class MenuService implements IMenuService{
	
	private MenuRepository menuRepository;
	
	private ArticleRepository articleRepository;
	
	@Autowired
	public MenuService(MenuRepository menuRepository, ArticleRepository articleRepository) {
		this.menuRepository = menuRepository;
		this.articleRepository = articleRepository;
	}
	
	@Override
	public MenuDTO getMenu(int menuId) {
		List<ArticleDTO> articles = menuRepository.getOne(menuId).getArticles().stream()
				.map(article -> new ArticleDTO(article)).collect(Collectors.toList());
		return new MenuDTO(articles);
	}

	@Override
	public MenuDTO deleteArticle(int menuId,int id) {
		Menu menu = menuRepository.getOne(menuId);
		List<Article> articles = menu.getArticles().stream().filter(article -> article.getId() != id).collect(Collectors.toList());
		menuRepository.save(menu);
		return new MenuDTO(articles.stream().map(article -> new ArticleDTO(article)).collect(Collectors.toList()));
	}

	@Override
	public MenuDTO addArticle(int menuId,int id) {
		Menu menu = menuRepository.getOne(menuId);
		Optional<Article> article =  articleRepository.findById(id);
		if(article.isEmpty()) {
			throw new NotFoundException("There is no article with the given id: " + id);
		}
		menu.getArticles().add(article.get());
		menuRepository.save(menu);
		return new MenuDTO(menu.getArticles().stream().map(elem -> new ArticleDTO(elem)).collect(Collectors.toList()));
	}

}
