package com.rest.RestaurantApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Menu;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.MenuDTO;
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
	public MenuDTO getMenu() {
		List<ArticleDTO> articles = menuRepository.getOne(420).getArticles().stream()
				.map(article -> new ArticleDTO(article)).collect(Collectors.toList());
		return new MenuDTO(articles);
	}

	@Override
	public MenuDTO deleteArticle(int id) {
		Menu menu = menuRepository.getOne(420);
		List<Article> articles = menu.getArticles().stream().filter(article -> article.getId() != id).collect(Collectors.toList());
		menuRepository.save(menu);
		return new MenuDTO(articles.stream().map(article -> new ArticleDTO(article)).collect(Collectors.toList()));
	}

	@Override
	public MenuDTO addArticle(int id) {
		Menu menu = menuRepository.getOne(420);
		menu.getArticles().add(articleRepository.findById(id).orElse(null));
		menuRepository.save(menu);
		return new MenuDTO(menu.getArticles().stream().map(article -> new ArticleDTO(article)).collect(Collectors.toList()));
	}

}
