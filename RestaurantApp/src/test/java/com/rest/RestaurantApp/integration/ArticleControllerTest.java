package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.services.ArticleService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ArticleControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ArticleService articleService;
	
	
	
	@Test
	public void testGetAll() throws Exception {
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article", ArticleDTO[].class);

		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, categories.length);
		
	}
	
	@Test
	public void testSearch_nameParam() throws Exception {
		
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article/search/?type=&name=es", ArticleDTO[].class);
        
		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, categories.length);
		
	}
	
	@Test
	void testSearch_noParams() {
		
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article/search/?type=&name=", ArticleDTO[].class);
        
		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, categories.length);
	}
	
	
	@Test
	void testSearch_typeParam() {
		
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article/search/?type=DESSERT&name=", ArticleDTO[].class);
        
		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, categories.length);
	}
	
	@Test
	void testSearch_typeAndNameParam_ExpectOne() {
		
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article/search/?type=DESSERT&name=ES", ArticleDTO[].class);
        
		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, categories.length);
	}
	
	@Test
	void testSearch_typeAndNameParam_ExpectNone() {
		
		ResponseEntity<ArticleDTO[]> responseEntity = restTemplate.getForEntity(
				"/api/article/search/?type=DESSERT&name=ESsdasd", ArticleDTO[].class);
        
		ArticleDTO[] categories = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, categories.length);
	}
	
	@Test
	public void testGetOne_ValidId() throws Exception {
		ResponseEntity<ArticleDTO> responseEntity = restTemplate.getForEntity(
				"/api/article/1", ArticleDTO.class);

		ArticleDTO article = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Dobos tortaaa", article.getName());
		
	}
	
	@Test
	public void testGetOne_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(
				"/api/article/15", String.class);

		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("There is no article with the given id: 15", responseEntity.getBody());
		
	}
	
	@Test
	public void testCreate_ValidArticleCreationDTO() throws Exception {
		int sizeBefore = articleService.getAll().size();
		ArticleCreationDTO article = new ArticleCreationDTO(new ArrayList<>(),"novi",500,600,"hladna",ArticleType.DRINK);
		
		ResponseEntity<ArticleDTO> responseEntity = restTemplate.postForEntity("/api/article", article, ArticleDTO.class);
		
		ArticleDTO newArticle = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("novi", newArticle.getName());
		int sizeAfter = articleService.getAll().size();
		assertEquals(sizeBefore+1, sizeAfter);
		
		//ZBOG GET ALL TESTA
		articleService.delete(newArticle.getId());
	}
	
	@Test
	public void testUpdate_ValidId() throws Exception {
		ArticleDTO newArticle = new ArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setMakingPrice(700);
		newArticle.setSellingPrice(800);
		newArticle.setIngredients(new ArrayList<>());
		newArticle.setType(ArticleType.APPETIZER);
		
		ResponseEntity<ArticleDTO> responseEntity = restTemplate.exchange(
				"/api/article/2", HttpMethod.PUT, new HttpEntity<ArticleDTO>(newArticle), ArticleDTO.class);
		
		ArticleDTO updatedArticle = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("kola", updatedArticle.getName());
		assertEquals(ArticleType.APPETIZER, updatedArticle.getType());
		
		ArticleDTO getUpdatedArticle = articleService.getOne(2);
		assertEquals("kola", getUpdatedArticle.getName());
		assertEquals(ArticleType.APPETIZER, getUpdatedArticle.getType());
	}
	
	@Test
	public void testUpdate_InvalidId() throws Exception {
		ArticleDTO newArticle = new ArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setMakingPrice(700);
		newArticle.setSellingPrice(800);
		newArticle.setIngredients(new ArrayList<>());
		newArticle.setType(ArticleType.APPETIZER);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/article/25", HttpMethod.PUT, new HttpEntity<ArticleDTO>(newArticle), String.class);

		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("There is no article with the given id: 25", responseEntity.getBody());
	}
	
	@Test
	public void testDelete_InvalidId() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/article/25", HttpMethod.DELETE,  new HttpEntity<Object>(null), String.class);

		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("There is no article with the given id: 25", responseEntity.getBody());
	}
	
	@Test
	public void testDelete_InvalidArticle_ArticleBeingPrepared() throws Exception {
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"/api/article/1", HttpMethod.DELETE,  new HttpEntity<Object>(null), String.class);

		
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals("Article with id 1 is currently being prepared", responseEntity.getBody());
	}
	
	@Test
	public void testDelete_ValidId() throws Exception {
		
		ArticleCreationDTO article = new ArticleCreationDTO(new ArrayList<>(),"novi",500,600,"hladna",ArticleType.DRINK);
		
		ResponseEntity<ArticleDTO> responseEntity = restTemplate.postForEntity("/api/article", article, ArticleDTO.class);
		
		ArticleDTO newArticle = responseEntity.getBody();
		
		int sizeBefore = articleService.getAll().size();
		ResponseEntity<String> responseEntityDelete = restTemplate.exchange(
				"/api/article/"+newArticle.getId(), HttpMethod.DELETE,  new HttpEntity<Object>(null), String.class);

		
		assertEquals(HttpStatus.OK, responseEntityDelete.getStatusCode());
		int sizeAfter = articleService.getAll().size();
		assertEquals(sizeBefore, sizeAfter+1);
	}
}
