package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.MenuDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.ArticleService;
import com.rest.RestaurantApp.services.MenuService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuServiceTest {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private ArticleService articleService;
	
	@Test
	void testGetMenu() {
		
		MenuDTO result = menuService.getMenu(420);
		
		assertEquals(result.getArticles().size(), 10);
	}
	
	@Test
	void testDeleteArticle() {
		
		MenuDTO result = menuService.deleteArticle(420,2);
		
		assertEquals(result.getArticles().size(), 9);
		menuService.addArticle(420, 2);
	}
	
	@Test
	void testAddArticle_ValidId() {
		ArticleCreationDTO article = new ArticleCreationDTO(new ArrayList<>(),"novi",500,600,"hladna",ArticleType.DRINK);
		ArticleDTO newArticle = articleService.create(article);
		MenuDTO result = menuService.addArticle(420,newArticle.getId());
		
		assertEquals(result.getArticles().size(), 11);
		menuService.deleteArticle(420, newArticle.getId());
	}
	
	@Test
	void testAddArticle_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			menuService.addArticle(420,55);
            });
	}
}
