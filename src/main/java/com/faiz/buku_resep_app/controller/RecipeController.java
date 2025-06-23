package com.faiz.buku_resep_app.controller;

import com.faiz.buku_resep_app.model.Ingredient;
import com.faiz.buku_resep_app.model.Recipe;
import com.faiz.buku_resep_app.model.User;
import com.faiz.buku_resep_app.repository.RecipeRepository;
import com.faiz.buku_resep_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000") // This allows all standard methods including PUT and DELETE
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Recipe newRecipe(@RequestBody Recipe newRecipe) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Error: User with ID 1 not found."));
        newRecipe.setUser(user);
        return recipeRepository.save(newRecipe);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeDetails) {
        // Find the existing recipe in the database
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // Update the fields of the existing recipe with the new details
        recipe.setName(recipeDetails.getName());
        recipe.setDescription(recipeDetails.getDescription());
        recipe.setImageUrl(recipeDetails.getImageUrl());
        recipe.setReferenceLink(recipeDetails.getReferenceLink());

        // Clear the old ingredients and add the new ones.
        recipe.getIngredients().clear();
        for (Ingredient ingredient : recipeDetails.getIngredients()) {
            ingredient.setRecipe(recipe); // Ensure the back-reference is set
            recipe.getIngredients().add(ingredient);
        }

        // Save the updated recipe back to the database
        final Recipe updatedRecipe = recipeRepository.save(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    // --- THIS IS THE NEW METHOD FOR DELETING A RECIPE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        // In a real application with roles, you would first get the current user's authentication details.
        // Then, you would check if that user has an 'ADMIN' role.
        // For example: if (!currentUser.getRoles().contains("ROLE_ADMIN")) { return ResponseEntity.status(403).build(); }

        // Check if the recipe exists before trying to delete it
        if (!recipeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        recipeRepository.deleteById(id);

        // Return a success response with a message
        return ResponseEntity.ok(Map.of("message", "Recipe with ID " + id + " has been deleted successfully."));
    }
}
