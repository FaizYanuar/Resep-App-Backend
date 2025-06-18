package com.faiz.buku_resep_app.controller;

import com.faiz.buku_resep_app.model.Ingredient;
import com.faiz.buku_resep_app.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientController {
    
    @Autowired
    private IngredientRepository ingredientRepository;

    @PostMapping("/api/ingredients")
    Ingredient newIngredient(@RequestBody Ingredient newIngredient){
        return ingredientRepository.save(newIngredient);
    }
}
