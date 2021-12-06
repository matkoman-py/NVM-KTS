package com.rest.RestaurantApp.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.services.IngredientService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class IngredientControllerUnitTest {

    private static final String URL_PREFIX = "/api/ingredient";

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<IngredientDTO> found = new ArrayList<>();
        IngredientDTO ingredient = new IngredientDTO(0, "Chicken", false);
        found.add(ingredient);

        Mockito.when(ingredientService.getAll()).thenReturn(found);

        mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(0)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("Chicken")))
                .andExpect(jsonPath("$.[*].allergen").value(hasItem(false)));

        verify(ingredientService, times(1)).getAll();
    }

    @Test
    public void testGetOne() throws Exception {
        IngredientDTO ingredient = new IngredientDTO(0, "Chicken", false);

        Mockito.when(ingredientService.getOne(0)).thenReturn(ingredient);

        mockMvc.perform(get(URL_PREFIX + "/0")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("Chicken"))
                .andExpect(jsonPath("$.allergen").value(false));

        verify(ingredientService, times(1)).getOne(0);
    }

    @Test
    public void testGetOne_IngredientNotFound() throws Exception {
        Mockito.when(ingredientService.getOne(1)).thenReturn(null);

        mockMvc.perform(get(URL_PREFIX + "/1")).andExpect(status().isNotFound());

        verify(ingredientService, times(1)).getOne(1);
    }

}
