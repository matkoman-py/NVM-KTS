package com.rest.RestaurantApp.integration;

import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testGetAll() {
        List<IngredientDTO> ingredients = ingredientService.getAll();

        assertEquals(3, ingredients.size());
    }

    @Test
    public void testGetOne_ValidId() {
        IngredientDTO ingredient = ingredientService.getOne(1);

        assertEquals((int) ingredient.getId(), 1);
        assertEquals(ingredient.getName(), "Vanila");
    }

    @Test
    public void testGetOne_InvalidId() {
        assertThrows(NotFoundException.class, () -> {
            IngredientDTO result = ingredientService.getOne(11);
        });
    }

    @Test
    public void testDelete_ValidId() {
        IngredientDTO createdIngredient = ingredientService.create(new IngredientDTO("Sastojak 3", false));
        int oldSize = ingredientService.getAll().size();
        IngredientDTO ingredient = ingredientService.delete(createdIngredient.getId());

        List<IngredientDTO> ingredients = ingredientService.getAll();
        int newSize = ingredients.size();

        assertEquals(oldSize - 1, newSize);
        assertEquals((int) ingredient.getId(), createdIngredient.getId());
        assertThrows(NoSuchElementException.class, () -> ingredients.stream().filter(i -> i.getId() == createdIngredient.getId())
                .findFirst().get());


    }

    @Test
    public void testDelete_InvalidId() {
        assertThrows(NotFoundException.class, () -> {
            IngredientDTO result = ingredientService.delete(11);
        });
    }

    @Test
    public void testCreate_Valid() {
        int oldSize = ingredientService.getAll().size();
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setAllergen(true);
        ingredientDTO.setName("Sastojak 1");

        IngredientDTO created = ingredientService.create(ingredientDTO);
        List<IngredientDTO> ingredients = ingredientService.getAll();
        int newSize = ingredients.size();

        ingredientService.delete(created.getId());

        assertEquals((int) created.getId(), 4);
        assertEquals(created.getName(), "Sastojak 1");
        assertEquals(oldSize + 1, newSize);

    }

    @Test
    public void testUpdate_Valid() {
        int oldSize = ingredientService.getAll().size();
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setAllergen(true);
        ingredientDTO.setName("Sastojak 1");

        IngredientDTO updated = ingredientService.update(2, ingredientDTO);
        List<IngredientDTO> ingredients = ingredientService.getAll();
        int newSize = ingredients.size();

        assertEquals((int) updated.getId(), 2);
        assertEquals(updated.getName(), "Sastojak 1");
        assertEquals(oldSize, newSize);

//        ingredientService.update(2, new IngredientDTO("Cokolada", true));
    }

    @Test
    public void testUpdate_InvalidId() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setAllergen(true);
        ingredientDTO.setName("Sastojak 1");

        assertThrows(NotFoundException.class, () -> ingredientService.update(11, ingredientDTO));
    }
}
