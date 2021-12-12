package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.domain.Order;
import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.dto.OrderDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientService implements IIngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientDTO> getAll() {
        return ingredientRepository.findAll().stream().filter(i -> !i.isDeleted()).map(IngredientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO getOne(int id) {
        Optional<Ingredient> ingredient =  ingredientRepository.findById(id);
        if(ingredient.isEmpty()) {
            throw new NotFoundException("There is no ingredient with the given id: " + id);
        }
        return new IngredientDTO(ingredient.get());
    }

    @Override
    public IngredientDTO delete(int id) {
        Optional<Ingredient> ingredient =  ingredientRepository.findById(id);
        if(ingredient.isEmpty()) {
            throw new NotFoundException("There is no ingredient with the given id: " + id);
        }
        Ingredient delIngredient = ingredient.get();
        delIngredient.setDeleted(true);
        ingredientRepository.save(delIngredient);
        return new IngredientDTO(delIngredient);
    }

    @Override
    public IngredientDTO create(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        ingredient.setDeleted(false);
        ingredient.setAllergen(ingredientDTO.isAllergen());

        return new IngredientDTO(ingredientRepository.save(ingredient));
    }

    @Override
    public IngredientDTO update(int id, IngredientDTO ingredientDTO) {
        Optional<Ingredient> oldIngredient = ingredientRepository.findById(id);
        if(oldIngredient.isEmpty()) {
            throw new NotFoundException("There is no ingredient with the given id: " + id);
        }

        Ingredient ingredient = oldIngredient.get();

        if(ingredient.isDeleted()) return null;

        ingredient.setName(ingredientDTO.getName());
        ingredient.setAllergen(ingredientDTO.isAllergen());

        Ingredient updatedIngredient = ingredientRepository.save(ingredient);


        return new IngredientDTO(updatedIngredient);
    }
}
