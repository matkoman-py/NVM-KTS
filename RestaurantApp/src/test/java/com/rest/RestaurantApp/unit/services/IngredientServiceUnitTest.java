package com.rest.RestaurantApp.unit.services;

import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import com.rest.RestaurantApp.services.IngredientService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class IngredientServiceUnitTest {

    @Autowired
    private IngredientService ingredientService;

    @MockBean
    private IngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient("Chicken", false);
        ingredient.setId(0);
        ingredient.setDeleted(false);
        ingredients.add(ingredient);

        given(ingredientRepository.findAll()).willReturn(ingredients);
        given(ingredientRepository.findById(0)).willReturn(Optional.of(ingredient));
    }

    @Test
    public void testGetAll() {
        List<IngredientDTO> found = ingredientService.getAll();
        System.out.println(ingredientService.getAll().size());
//        verify(ingredientRepository, times(1)).findAll();
        assertEquals(1, found.size());
    }

    @Test
    public void testGetOne() {
        IngredientDTO ingredient = ingredientService.getOne(0);

        verify(ingredientRepository, times(1)).getById(0);
        assertEquals(0, (int) ingredient.getId());
    }
}
