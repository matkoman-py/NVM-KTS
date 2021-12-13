package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
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
		
		assertEquals(result.getName(), "Nova torta");
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
		
		assertEquals(result.size(), 1);
	}
	
	@Test
	void testDelete_ValidId() {
		
		List<SuggestedArticleDTO> result1 = suggestedArticleService.getAll();
		SuggestedArticleDTO employee = suggestedArticleService.delete(1);
		List<SuggestedArticleDTO> result2 = suggestedArticleService.getAll();
		
		assertNotEquals(result1.size(),result2.size());
	}
	
	@Test
	void testDelete_InvalidId() {
		
		assertThrows(NotFoundException.class,()->{
			suggestedArticleService.delete(5);
		});
	}
}
