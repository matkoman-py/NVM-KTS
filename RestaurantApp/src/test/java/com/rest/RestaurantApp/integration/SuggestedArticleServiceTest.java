package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.domain.SuggestedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.SuggestedArticleService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SuggestedArticleServiceTest {
	
	@Autowired
	private SuggestedArticleService suggestedArticleService;
	
	@Test
	void testGetOne_ValidId() {
		
		SuggestedArticleDTO result = suggestedArticleService.getOne(1);
		
		assertEquals("Nova torta", result.getName());
	}
	
	@Test
	void testGetOne_InvalidId() {
		
		assertThrows(NotFoundException.class, ()->{
			suggestedArticleService.getOne(3);
            });
	}
	
	@Test
	void testGetAll() {
		
		List<SuggestedArticleDTO> result = suggestedArticleService.getAll();
		
		assertEquals(2, result.size());
	}
	
	@Test
	void testDelete_ValidId() {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		
		List<SuggestedArticleDTO> result1 = suggestedArticleService.getAll();
		suggestedArticleService.delete(createdSuggestedArticle.getId());
		List<SuggestedArticleDTO> result2 = suggestedArticleService.getAll();
		
		assertEquals(result1.size(),result2.size() + 1);
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			suggestedArticleService.delete(5);
		});
	}
	
	@Test
	void testCreate() {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		
		SuggestedArticleDTO fromDatabase = suggestedArticleService.getOne(createdSuggestedArticle.getId());
		assertEquals("new cake", fromDatabase.getName());
		assertEquals(100, fromDatabase.getSuggestedMakingPrice());
		assertEquals(150, fromDatabase.getSuggestedSellingPrice());
		assertEquals("sehr gut", fromDatabase.getDescription());
		assertEquals(ArticleType.DESSERT, fromDatabase.getType());
		
		suggestedArticleService.delete(createdSuggestedArticle.getId());
	}
	
	@Test
	void testUpdate_ValidId() {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);

		SuggestedArticleDTO updateSuggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake2","sehr gut2",200,250,ArticleType.APPETIZER);
		suggestedArticleService.update(createdSuggestedArticle.getId(), updateSuggestedArticle);
		
		SuggestedArticleDTO updatedSuggestedArticle = suggestedArticleService.getOne(createdSuggestedArticle.getId());
		assertEquals("new cake2", updatedSuggestedArticle.getName());
		assertEquals(200, updatedSuggestedArticle.getSuggestedMakingPrice());
		assertEquals(250, updatedSuggestedArticle.getSuggestedSellingPrice());
		assertEquals("sehr gut2", updatedSuggestedArticle.getDescription());
		assertEquals(ArticleType.APPETIZER, updatedSuggestedArticle.getType());
		
		suggestedArticleService.delete(createdSuggestedArticle.getId());
	}
	
	@Test
	void testUpdate_InvalidId() {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		assertThrows(NotFoundException.class, () -> {
			suggestedArticleService.update(11, suggestedArticle);
		});
	}
	
	@Test
	void testApprove_ValidId() {

		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		
		int beforeApprove = suggestedArticleService.getAll().size();
		suggestedArticleService.approve(createdSuggestedArticle.getId());
		int afterApprove = suggestedArticleService.getAll().size();	
		assertEquals(beforeApprove, afterApprove + 1);
	}
	
	@Test
	void testApprove_InvalidId() {
		assertThrows(NotFoundException.class, () -> {
			suggestedArticleService.approve(11);
		});
	}
}