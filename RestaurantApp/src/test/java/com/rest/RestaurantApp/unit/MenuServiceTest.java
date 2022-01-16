package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Menu;
import com.rest.RestaurantApp.domain.PriceInfo;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.MenuDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.MenuRepository;
import com.rest.RestaurantApp.services.MenuService;

@SpringBootTest
public class MenuServiceTest {
	@MockBean
	private MenuRepository menuRepository;
	@MockBean
	private ArticleRepository articleRepository;
	@Autowired
	private MenuService menuService;
	
	@BeforeEach
	public void setup() {
		List<PriceInfo> price1 = new ArrayList<>();
		List<PriceInfo> price2 = new ArrayList<>();
		List<PriceInfo> price3 = new ArrayList<>();
		List<Article> articles = new ArrayList<>();
		Article article1 = new Article("kola", "hladna", ArticleType.DRINK);
		Article article2 = new Article("pica", "dobra", ArticleType.MAIN_COURSE);
		Article article3 = new Article("krofne", "slatke", ArticleType.DESSERT);
		article1.setId(1);
		price1.add(new PriceInfo(new Date(),500,600,article1));
		article1.setPrices(price1);
		article2.setId(2);
		price2.add(new PriceInfo(new Date(),500,600,article2));
		article2.setPrices(price2);
		article3.setId(3);
		price3.add(new PriceInfo(new Date(),500,600,article3));
		article3.setPrices(price3);
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		Menu menu = new Menu();
		menu.setId(1);
		menu.setArticles(new HashSet<Article>(articles));
		given(menuRepository.getOne(1)).willReturn(menu);
		given(menuRepository.save(menu)).willReturn(menu);
		
		List<PriceInfo> price4 = new ArrayList<>();
		Article article4 = new Article("dobos torta", "slatke", ArticleType.DESSERT);
		article4.setId(4);
		price4.add(new PriceInfo(new Date(),500,600,article4));
		article4.setPrices(price4);
		given(articleRepository.findById(4)).willReturn(java.util.Optional.of(article4));
		given(articleRepository.findById(5)).willReturn(Optional.empty());
	}
	
	@Test
	void testGetMenu() {
		
		MenuDTO result = menuService.getMenu(1);
		
		assertEquals(result.getArticles().size(), 3);
	}
	
	@Test
	void testDeleteArticle() {
		
		MenuDTO result = menuService.deleteArticle(1,2);
		
		assertEquals(result.getArticles().size(), 2);
	}
	
	@Test
	void testAddArticle_ValidId() {
		
		MenuDTO result = menuService.addArticle(1,4);
		
		assertEquals(result.getArticles().size(), 4);
	}
	
	@Test
	void testAddArticle_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			menuService.addArticle(1,5);
            });
	}
}
