package com.rest.RestaurantApp.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.IngredientService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rest.RestaurantApp.repositories.IngredientRepository;


import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class IngredientServiceTest {

    @MockBean
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientService ingredientService;

    @BeforeEach
    public void setup() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient("Chicken", false);
        ingredient1.setId(0);
        ingredient1.setDeleted(false);
        ingredients.add(ingredient1);

        Ingredient ingredient2 = new Ingredient("Chicken", false);
        ingredient2.setId(1);
        ingredient2.setDeleted(false);
        ingredients.add(ingredient2);

        Ingredient ingredient3 = new Ingredient("Chicken", false);
        ingredient3.setId(2);
        ingredient3.setDeleted(false);
        ingredients.add(ingredient3);

        given(ingredientRepository.findAll()).willReturn(ingredients);
        given(ingredientRepository.findById(0)).willReturn(Optional.of(ingredient1));
        given(ingredientRepository.findById(1)).willReturn(Optional.of(ingredient2));
        given(ingredientRepository.save(ingredient1)).willReturn(ingredient1);
        given(ingredientRepository.save(ingredient2)).willReturn(ingredient2);


        Ingredient newIngredient = new Ingredient("Cheese", false);
        newIngredient.setId(3);
        newIngredient.setDeleted(false);

        given(ingredientRepository.save(newIngredient)).willReturn(newIngredient);

    }

    @Test
    public void testGetAll() {
        List<IngredientDTO> found = ingredientService.getAll();
        System.out.println(ingredientService.getAll().size());
//        verify(ingredientRepository, times(1)).findAll();
        assertEquals(3, found.size());
    }

    @Test
    public void testGetOne_ValidId() {
        IngredientDTO ingredient = ingredientService.getOne(0);

        assertEquals(0, (int) ingredient.getId());
    }

    @Test
    public void testGetOne_InvalidId() {
        assertThrows(NotFoundException.class, ()-> {
            IngredientDTO result = ingredientService.getOne(4);
        });
    }

    @Test
    public void testDelete_ValidId() {
        IngredientDTO ingredient = ingredientService.delete(0);

        assertEquals(0, (int) ingredient.getId());
    }

    @Test
    public void testDelete_InvalidId() {
        assertThrows(NotFoundException.class, ()-> {
            IngredientDTO result = ingredientService.delete(4);
        });
    }

    @Test
    public void testUpdate_ValidId() {
        IngredientDTO ingredient = new IngredientDTO("Milk", true);

        IngredientDTO result = ingredientService.update(1, ingredient);

        assertEquals("Milk", ingredient.getName());
        assertTrue(ingredient.isAllergen());
    }

    @Test
    public void testUpdate_InvalidId() {
        IngredientDTO ingredient = new IngredientDTO("Milk", true);

        assertThrows(NotFoundException.class, ()-> {
            IngredientDTO result = ingredientService.update(15, ingredient);
        });
    }
}
