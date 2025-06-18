// src/main/java/com/faiz/buku_resep_app/model/Ingredient.java
package com.faiz.buku_resep_app.model;

import jakarta.persistence.Column;     // Used for column annotations
import jakarta.persistence.Entity;     // Marks the class as a JPA entity
import jakarta.persistence.GeneratedValue; // Used for ID generation strategy
import jakarta.persistence.GenerationType; // Type of ID generation strategy
import jakarta.persistence.Id;         // Marks the primary key
import jakarta.persistence.JoinColumn; // Specifies the foreign key column
import jakarta.persistence.ManyToOne;  // Specifies a many-to-one relationship
import jakarta.persistence.Table;      // Specifies the table name in the database


@Entity
@Table(name = "ingredients")

public class Ingredient {

    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses MySQL's AUTO_INCREMENT strategy
    private Long id; // Unique ID for the ingredient, using Long to map BIGINT

    // Many-to-One relationship with Recipe
    // Many ingredients belong to one recipe
    @ManyToOne // Marks the Many-to-One relationship
    @JoinColumn(name = "recipe_id", nullable = false) // recipe_id is the foreign key column in the ingredients table, and it cannot be null
    private Recipe recipe; // Reference to the Recipe entity this ingredient belongs to

    @Column(nullable = false, length = 100) // 'name' column cannot be null, max length 100
    private String name; // Name of the ingredient (e.g., "Wheat Flour")

    @Column(nullable = false, length = 100) // 'quantity' column cannot be null, max length 100
    private String quantity; // Amount/measurement of the ingredient (e.g., "250 grams", "2 eggs")

    // --- Constructors (Required by JPA, especially the no-argument constructor) ---
    // If using Lombok, you can remove these manual constructors
    public Ingredient() {
    }

    // Constructor with parameters for creating new Ingredient objects
    public Ingredient(Recipe recipe, String name, String quantity) {
        this.recipe = recipe;
        this.name = name;
        this.quantity = quantity;
    }

    // --- Getters and Setters for all fields ---
    // If using Lombok, you can remove these manual getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    // This setter is crucial for managing the bidirectional relationship from the Recipe side
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    // --- toString() method (optional, highly recommended for debugging) ---
    // If using Lombok, @Data will automatically generate a toString()
    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                // Avoid printing the 'recipe' object directly in toString() to prevent StackOverflowError
                ", recipeId=" + (recipe != null ? recipe.getId() : "null") +
                '}';
    }
}
