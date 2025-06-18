package com.faiz.buku_resep_app.controller;

import com.faiz.buku_resep_app.model.Recipe;
import com.faiz.buku_resep_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @PostMapping
    Recipe newRecipe(@RequestBody Recipe newRecipe){
        return recipeRepository.save(newRecipe);
    }
}