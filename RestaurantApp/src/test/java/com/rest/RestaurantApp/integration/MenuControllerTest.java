package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.MenuDTO;
import com.rest.RestaurantApp.services.ArticleService;
import com.rest.RestaurantApp.services.MenuService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class MenuControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ArticleService articleService;
	
	@Test
	public void testGetMenu() throws Exception {
		ResponseEntity<MenuDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/menu", MenuDTO.class);

		MenuDTO menu = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, menu.getArticles().size());
	}
	
	@Test
	public void testAddArticle_ValidId() { 
		ArticleCreationDTO article = new ArticleCreationDTO(new ArrayList<>(), "novi", 500, 600, "hladna", ArticleType.DRINK);
		ArticleDTO newArticle = articleService.create(article);
		
		ResponseEntity<MenuDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/menu/add-article/"+newArticle.getId(), HttpMethod.PUT, new HttpEntity<Object>(null), MenuDTO.class);
		MenuDTO menu = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(11, menu.getArticles().size());
		menuService.deleteArticle(420, newArticle.getId());
		articleService.delete(newArticle.getId());
	}
	
	@Test
	public void testAddArticle_InvalidId() { 
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/menu/add-article/55", HttpMethod.PUT, new HttpEntity<Object>(null), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("There is no article with the given id: 55", responseEntity.getBody());
	}
	
	@Test
	public void testDeleteArticle() { 
		ResponseEntity<MenuDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").exchange(
				"/api/menu/delete-article/1", HttpMethod.PUT, new HttpEntity<Object>(null), MenuDTO.class);
		MenuDTO menu = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(9, menu.getArticles().size());
	}
}
