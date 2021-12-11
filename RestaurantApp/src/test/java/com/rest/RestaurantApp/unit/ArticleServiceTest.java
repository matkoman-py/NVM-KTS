package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.PriceInfo;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import com.rest.RestaurantApp.repositories.PriceInfoRepository;
import com.rest.RestaurantApp.services.ArticleService;

@SpringBootTest
public class ArticleServiceTest {
	
	@MockBean
	private ArticleRepository articleRepository;
	@MockBean
	private IngredientRepository ingredientRepository;
	@MockBean
	private PriceInfoRepository priceInfoRepository;
	@MockBean
	private Article article;
	@Autowired
	private ArticleService articleService;
	
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
		
		given(articleRepository.findAll()).willReturn(articles);
		given(articleRepository.findById(1)).willReturn(java.util.Optional.of(article1));
		given(articleRepository.findById(2)).willReturn(java.util.Optional.of(article2));
		given(articleRepository.findById(3)).willReturn(java.util.Optional.of(article3));
		given(articleRepository.findById(4)).willReturn(Optional.empty());
		given(articleRepository.save(article1)).willReturn(article1);
		
		
	
		Article newArticle = new Article("novi", "hladna", ArticleType.DRINK);
		PriceInfo pi = new PriceInfo(new Date(),500,600,newArticle);
		List<PriceInfo> priceNew = new ArrayList<>();
		newArticle.setId(5);
		priceNew.add(pi);
		newArticle.setPrices(priceNew);
		given(articleRepository.save(newArticle)).willReturn(newArticle);
	}
	
	@Test
	void testFindOne_ValidId() {
		
		ArticleDTO result = articleService.getOne(1);
		
		assertEquals(result.getName(), "kola");
	}
	
	@Test
	void testFindOne_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			articleService.getOne(4);
            });
	}
	
	@Test
	void testGetAllArticles() {
		
		List<ArticleDTO> returnedArticles = articleService.getAll();
		
		assertEquals(returnedArticles.size(), 3);
	}
	
	@Test
	void testDelete_ValidId() {
		
		ArticleDTO result = articleService.delete(1);
		
		assertEquals(result.getName(), "kola");
	}
	
	@Test
	void testDelete_InvalidId() {
		assertThrows(NotFoundException.class, ()->{
			articleService.delete(4);
            });
	}
	
	@Test
	void testCreate() {
		
		ArticleDTO result = articleService.create(new ArticleCreationDTO(new ArrayList<>(),"novi",500,600,"hladna",ArticleType.DRINK));
		
		assertEquals(result.getId(), 5);
	}
	
	@Test
	void testUpdate_ValidId() {
		
		ArticleDTO newArticle = new ArticleDTO();
		newArticle.setName("kola");
		newArticle.setDescription("bas dobar");
		newArticle.setMakingPrice(700);
		newArticle.setSellingPrice(800);
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
			articleService.update(15, newArticle);
            });
	}
}
