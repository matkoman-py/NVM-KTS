package com.rest.RestaurantApp.integration;

import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.services.IngredientService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class IngredientControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IngredientService ingredientService;

    @Test
    void testGetAll() {
        ResponseEntity<IngredientDTO[]> response = restTemplate.withBasicAuth("manager_test", "test")
                .getForEntity("/api/ingredient", IngredientDTO[].class);

        IngredientDTO[] ingredients = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredients.length, 8);

    }
    
    @Test
    void testSearch() {
        ResponseEntity<IngredientDTO[]> response = restTemplate.withBasicAuth("manager_test", "test")
                .getForEntity("/api/ingredient/search?name=ba&type=allergen", IngredientDTO[].class);

        IngredientDTO[] ingredients = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredients.length, 1);

    }

    @Test
    void testGetOne_ValidId() {
        ResponseEntity<IngredientDTO> response = restTemplate.getForEntity("/api/ingredient/1", IngredientDTO.class);

        IngredientDTO ingredient = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ingredient.getId(), 1);
        assertEquals(ingredient.getName(), "Vanila");
        assertFalse(ingredient.isAllergen());
    }

    @Test
    void testGetOne_InvalidId() {
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("/api/ingredient/55", String.class);


        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("There is no ingredient with the given id: 55", responseEntity.getBody());
    }

    @Test
    void testCreate_ValidIngredient() {
        int size = ingredientService.getAll().size();
        IngredientDTO ingredient = new IngredientDTO("Sastojak 1", false);

        ResponseEntity<IngredientDTO> response = restTemplate.postForEntity("/api/ingredient", ingredient, IngredientDTO.class);

        IngredientDTO createdIngredient = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdIngredient.getName(), "Sastojak 1");
        assertFalse(createdIngredient.isAllergen());
        assertEquals(createdIngredient.getId(), 9);

        List<IngredientDTO> ingredients = ingredientService.getAll();
        assertEquals(ingredients.size(), size + 1);
        assertEquals(ingredients.get(ingredients.size() - 1).getName(), "Sastojak 1");
        assertFalse(ingredients.get(ingredients.size() - 1).isAllergen());

        ingredientService.delete(createdIngredient.getId());
    }

    @Test
    public void testUpdate_ValidIngredient() {
        int size = ingredientService.getAll().size();
        IngredientDTO ingredient = new IngredientDTO("Sastojak 2", true);

        ResponseEntity<IngredientDTO> response = restTemplate.exchange("/api/ingredient/3", HttpMethod.PUT,
                new HttpEntity<>(ingredient), IngredientDTO.class);

        IngredientDTO updatedIngredient = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedIngredient.getName(), "Sastojak 2");
        assertTrue(updatedIngredient.isAllergen());

        List<IngredientDTO> ingredients = ingredientService.getAll();
        assertEquals(ingredients.size(), size);
        assertEquals(ingredients.stream().filter(i -> i.getId() == 3).findFirst().get().getName(), "Sastojak 2");
    }

    @Test
    public void testDelete_ValidId() {
        IngredientDTO ingredient = ingredientService.create(new IngredientDTO("Sastojak 3", false));
        int size = ingredientService.getAll().size();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/ingredient/" + ingredient.getId(), HttpMethod.DELETE, new HttpEntity<>(null),
                String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(size - 1, ingredientService.getAll().size());
    }

    @Test
    public void testDelete_InvalidId() {
        int size = ingredientService.getAll().size();

        ResponseEntity<String> response = restTemplate.exchange("/api/ingredient/55", HttpMethod.DELETE,
                new HttpEntity<>(null), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(size, ingredientService.getAll().size());
    }
}
