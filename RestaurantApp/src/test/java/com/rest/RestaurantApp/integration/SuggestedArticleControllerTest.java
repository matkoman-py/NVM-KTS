package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;
import com.rest.RestaurantApp.services.SuggestedArticleService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SuggestedArticleControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SuggestedArticleService suggestedArticleService;
	
	@Test
	public void testGetOne_ValidId() throws Exception {
		ResponseEntity<SuggestedArticleDTO> responseEntity = restTemplate.getForEntity(
				"/api/suggestedArticle/2", SuggestedArticleDTO.class);

		SuggestedArticleDTO suggestedArticle = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(suggestedArticle.getName(), "Nova krofna");
	}
	
	@Test
	public void testGetOne_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/api/suggestedArticle/15", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test 
	public void testGetAll() {
		ResponseEntity<SuggestedArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/suggestedArticle", SuggestedArticleDTO[].class);
		
		SuggestedArticleDTO[] privilegedUsers = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, privilegedUsers.length);
	}
	
	@Test
	public void testDelete_ValidId() { 
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		
		int before_delete = suggestedArticleService.getAll().size();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/suggestedArticle/" + createdSuggestedArticle.getId(), HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);
		int after_delete = suggestedArticleService.getAll().size();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(before_delete, after_delete + 1);
	}
	
	@Test
	public void testDelete_InvalidId() { 

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/suggestedArticle/1231", HttpMethod.DELETE, new HttpEntity<Object>(null), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testApprove_ValidId() throws Exception {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);
		
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		
		int before_approve = suggestedArticleService.getAll().size();
		ResponseEntity<SuggestedArticleDTO> responseEntity = restTemplate.getForEntity(
				"/api/suggestedArticle/approve/" + createdSuggestedArticle.getId(), SuggestedArticleDTO.class);
		int after_approve = suggestedArticleService.getAll().size();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(before_approve, after_approve + 1);
	}
	
	@Test
	public void testApprove_InvalidId() throws Exception {
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/api/suggestedArticle/approve/12311", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	@Test
	public void testCreate() throws Exception {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);

		ResponseEntity<SuggestedArticleDTO> responseEntity = restTemplate.exchange(
				"/api/suggestedArticle",HttpMethod.POST ,new HttpEntity<SuggestedArticleDTO>(suggestedArticle), SuggestedArticleDTO.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("new cake", suggestedArticleService.getOne(responseEntity.getBody().getId()).getName());
		assertEquals("sehr gut", suggestedArticleService.getOne(responseEntity.getBody().getId()).getDescription());
		assertEquals(100, suggestedArticleService.getOne(responseEntity.getBody().getId()).getSuggestedMakingPrice());
		assertEquals(150, suggestedArticleService.getOne(responseEntity.getBody().getId()).getSuggestedSellingPrice());
		assertEquals(ArticleType.DESSERT, suggestedArticleService.getOne(responseEntity.getBody().getId()).getType());
		
		suggestedArticleService.delete(responseEntity.getBody().getId());
	}
	
	@Test
	public void testUpdate_ValidId() throws Exception {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);

		SuggestedArticleDTO updateSuggestedArticle = new SuggestedArticleDTO(1,ingredients,"1new cake","1sehr gut",1100,1150,ArticleType.APPETIZER);

		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		ResponseEntity<SuggestedArticleDTO> responseEntity = restTemplate.exchange(
				"/api/suggestedArticle/" + createdSuggestedArticle.getId(),HttpMethod.PUT ,new HttpEntity<SuggestedArticleDTO>(updateSuggestedArticle), SuggestedArticleDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("1new cake", suggestedArticleService.getOne(responseEntity.getBody().getId()).getName());
		assertEquals("1sehr gut", suggestedArticleService.getOne(responseEntity.getBody().getId()).getDescription());
		assertEquals(1100, suggestedArticleService.getOne(responseEntity.getBody().getId()).getSuggestedMakingPrice());
		assertEquals(1150, suggestedArticleService.getOne(responseEntity.getBody().getId()).getSuggestedSellingPrice());
		assertEquals(ArticleType.APPETIZER, suggestedArticleService.getOne(responseEntity.getBody().getId()).getType());
		
		suggestedArticleService.delete(responseEntity.getBody().getId());
	}
	
	@Test
	public void testUpdate_InvalidId() throws Exception {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(1,ingredients,"new cake","sehr gut",100,150,ArticleType.DESSERT);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/suggestedArticle/1231",HttpMethod.PUT ,new HttpEntity<SuggestedArticleDTO>(suggestedArticle), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
