package com.rest.RestaurantApp.unit;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.domain.SalaryInfo;
import com.rest.RestaurantApp.domain.SuggestedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import com.rest.RestaurantApp.repositories.PriceInfoRepository;
import com.rest.RestaurantApp.repositories.SuggestedArticleRepository;
import com.rest.RestaurantApp.services.SuggestedArticleService;

@SpringBootTest
public class SuggestedArticleServiceTest {
	
	@MockBean
	private ArticleRepository articleRepository;
	
	@MockBean
	private SuggestedArticleRepository suggestedArticleRepository;
	
	@MockBean
	private IngredientRepository ingredientRepository;
	
	@MockBean
	private PriceInfoRepository priceInfoRepository;
	
	@MockBean
	private SuggestedArticle siggestedArticle;
	
	@Autowired
	private SuggestedArticleService suggestedArticleService;
	
	@BeforeEach
	public void setup() {
		List<SuggestedArticle> suggestedArticles = new ArrayList<>();
		Set<Ingredient> ingredients1 = new HashSet<>();
		Ingredient ingredient1 = new Ingredient("milk",true);
		Ingredient ingredient2 = new Ingredient("butter",true);

		ingredients1.add(ingredient1);
		ingredients1.add(ingredient2);
		SuggestedArticle suggestedArticle1 = new SuggestedArticle("kola", "bas dobar", 100, 200, ArticleType.DRINK);
		SuggestedArticle suggestedArticle2 = new SuggestedArticle("kol1a", "hladna", 100, 200, ArticleType.DRINK);
		SuggestedArticle suggestedArticle3 = new SuggestedArticle("kola2", "hladna", 100, 200, ArticleType.DRINK);
		
		suggestedArticle1.setIngredients(ingredients1);
		suggestedArticle2.setIngredients(ingredients1);
		suggestedArticle3.setIngredients(ingredients1);
		
		suggestedArticles.add(suggestedArticle1);
		suggestedArticles.add(suggestedArticle2);
		suggestedArticles.add(suggestedArticle3);

		given(suggestedArticleRepository.findAll()).willReturn(suggestedArticles);
		given(suggestedArticleRepository.findById(1)).willReturn(java.util.Optional.of(suggestedArticle1));
		given(suggestedArticleRepository.findById(2)).willReturn(java.util.Optional.of(suggestedArticle2));
		given(suggestedArticleRepository.findById(3)).willReturn(java.util.Optional.of(suggestedArticle3));
		given(suggestedArticleRepository.findById(4)).willReturn(Optional.empty());
		given(suggestedArticleRepository.save(suggestedArticle1)).willReturn(suggestedArticle1);
		
		Article newArticle1 = new Article(suggestedArticle1.getName(), suggestedArticle1.getDescription(), suggestedArticle1.getType());
		Article newArticle2 = new Article(suggestedArticle2.getName(), suggestedArticle2.getDescription(), suggestedArticle2.getType());
		Article newArticle3 = new Article(suggestedArticle3.getName(), suggestedArticle3.getDescription(), suggestedArticle3.getType());

		given(articleRepository.save(newArticle1)).willReturn(newArticle1);
		given(articleRepository.save(newArticle2)).willReturn(newArticle2);
		given(articleRepository.save(newArticle3)).willReturn(newArticle3);

		given(ingredientRepository.findById(1)).willReturn(java.util.Optional.of(ingredient1));
		given(ingredientRepository.findById(1)).willReturn(java.util.Optional.of(ingredient2));

		SuggestedArticle newSuggestedArticle = new SuggestedArticle("kola", "bas dobar", 100, 200, ArticleType.DRINK);
		newSuggestedArticle.setId(5);
		newSuggestedArticle.setIngredients(new HashSet<>());
		
		given(suggestedArticleRepository.save(newSuggestedArticle)).willReturn(newSuggestedArticle);
	}
	
	@Test
	void testCreate() {
		SuggestedArticleDTO suggestedArticle = new SuggestedArticleDTO(5,new HashSet<>(),"kola", "hladna", 100, 200, ArticleType.DRINK);

		SuggestedArticleDTO createdArticle = suggestedArticleService.create(suggestedArticle);
		
		assertEquals("kola", createdArticle.getName());
		assertEquals("bas dobar", createdArticle.getDescription());
		assertEquals(100, createdArticle.getSuggestedMakingPrice());
		assertEquals(200, createdArticle.getSuggestedSellingPrice());
		assertEquals(ArticleType.DRINK, createdArticle.getType());
	}
	
	@Test
	void testFindOne_ValidId() {
		
		SuggestedArticleDTO result = suggestedArticleService.getOne(1);
		assertEquals("kola", result.getName());
	}
	
	@Test
	void testFindOne_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			suggestedArticleService.getOne(4);
            });
	}
	
	@Test
	void testGetAllArticles() {
		
		List<SuggestedArticleDTO> returnedArticles = suggestedArticleService.getAll();
		
		assertEquals(3, returnedArticles.size());
	}
	
	@Test
	void testDelete_ValidId() {
		
		SuggestedArticleDTO result = suggestedArticleService.delete(1);
		
		assertEquals("kola", result.getName());
	}
	
	@Test
	void testDelete_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			suggestedArticleService.delete(4);
            });
	}
	
	@Test
	void testUpdate_ValidId() {
		
		SuggestedArticleDTO newArticle = new SuggestedArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setIngredients(new HashSet<IngredientDTO>());
		
		SuggestedArticleDTO result = suggestedArticleService.update(2, newArticle);
		assertEquals("kola", result.getName());
		assertEquals("bas dobar", result.getDescription());
	}
	
	@Test
	void testUpdate_InvalidId() {
		
		SuggestedArticleDTO newArticle = new SuggestedArticleDTO();

		assertThrows(NotFoundException.class, ()->{
			suggestedArticleService.update(15, newArticle);
            });
	}
	
	@Test
	void testApprove_InvalidId() {

		assertThrows(NotFoundException.class, ()->{
			suggestedArticleService.approve(11);
            });
	}
	
	@Test
	void testApprove_ValidId() {
		
		SuggestedArticleDTO result = suggestedArticleService.approve(1);
		assertEquals("kola", result.getName());
	}
}
