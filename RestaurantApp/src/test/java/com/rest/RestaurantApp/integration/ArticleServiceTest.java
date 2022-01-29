package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.exceptions.ArticlePreparingException;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.ArticleService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ArticleServiceTest {
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	void testGetAllArticles() {
		
		List<ArticleDTO> returnedArticles = articleService.getAll();
		
		assertEquals(returnedArticles.size(), 10);
	}
	
	@Test
	void testFindOne_ValidId() {
		
		ArticleDTO result = articleService.getOne(2);
		
		assertEquals("Jagnjetina", result.getName());
	}
	
	@Test
	void testSearch_noParams() {
		
		List<ArticleDTO> result = articleService.search("","");
		
		assertEquals(result.size(), 10);
	}
	
	@Test
	void testSearch_nameParam() {
		
		List<ArticleDTO> result = articleService.search("","ES");
		
		assertEquals(result.size(), 3);
	}
	
	@Test
	void testSearch_typeParam() {
		
		List<ArticleDTO> result = articleService.search("DESSERT","");
		
		assertEquals(result.size(), 3);
	}
	
	@Test
	void testSearch_typeAndNameParam_ExpectOne() {
		
		List<ArticleDTO> result = articleService.search("DESSERT","ES");
		
		assertEquals(result.size(), 1);
	}
	
	@Test
	void testSearch_typeAndNameParam_ExpectNone() {
		
		List<ArticleDTO> result = articleService.search("DESSERT","fapiofhaf");
		
		assertEquals(result.size(), 0);
	}
	
	@Test
	void testFindOne_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			articleService.getOne(54);
            });
	}
	
	@Test
	void testDelete_ValidId() {
		ArticleCreationDTO article = new ArticleCreationDTO(new ArrayList<>(),"novi",500,600,"hladna",ArticleType.DRINK);
		ArticleDTO newArticle = articleService.create(article);
		
		int returnedArticlesBefore = articleService.getAll().size();
		ArticleDTO result = articleService.delete(newArticle.getId());
		int returnedArticlesAfter = articleService.getAll().size();
		assertEquals(result.getName(), newArticle.getName());
		assertEquals(returnedArticlesBefore-1, returnedArticlesAfter);
	}
	
	@Test
	void testDelete_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			articleService.delete(54);
            });
	}
	
	@Test
	void testDelete_InvalidArticle_ArticleBeingPrepared() {
		assertThrows(ArticlePreparingException.class, ()->{
			articleService.delete(1);
            });
	}
	
	@Test
	void testCreate() {
		int returnedArticlesBefore = articleService.getAll().size();
		ArticleDTO result = articleService.create(new ArticleCreationDTO(new ArrayList<>(),"novii",500,600,"hladna",ArticleType.DRINK));
		int returnedArticlesAfter = articleService.getAll().size();
		assertEquals("novii", result.getName());
		assertEquals(returnedArticlesBefore+1, returnedArticlesAfter);
		articleService.delete(result.getId());
	}
	
	@Test
	void testUpdate_ValidId() {
		
		ArticleDTO newArticle = new ArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setMakingPrice(700);
		newArticle.setSellingPrice(800);
		newArticle.setType(ArticleType.DRINK);
		newArticle.setIngredients(new ArrayList<>());
		
		ArticleDTO result = articleService.update(1, newArticle);
		assertEquals(result.getName(), "kola");
		assertEquals(result.getDescription(), "bas dobar");
		assertEquals(result.getSellingPrice(), 800);
		assertEquals(result.getMakingPrice(), 700);
	}
	
	@Test
	void testUpdate_InvalidId() {
		
		ArticleDTO newArticle = new ArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setMakingPrice(700);
		newArticle.setSellingPrice(800);
		newArticle.setIngredients(new ArrayList<>());
		assertThrows(NotFoundException.class, ()->{
			articleService.update(55, newArticle);
            });
	}
}
