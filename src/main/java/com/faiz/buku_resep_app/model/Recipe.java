// src/main/java/com/faiz/buku_resep_app/model/Recipe.java
package com.faiz.buku_resep_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime; // Digunakan untuk tipe data tanggal dan waktu
import java.util.ArrayList;     // Untuk inisialisasi daftar bahan
import java.util.List;          // Untuk daftar bahan

@Entity
@Table(name = "recipes") // Memetakan entitas Recipe ke tabel 'recipes' di database
// Anotasi Lombok opsional:
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Recipe {

    @Id // Menandai 'id' sebagai primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Menggunakan strategi AUTO_INCREMENT MySQL
    private Long id; // ID unik untuk resep, menggunakan Long untuk memetakan BIGINT

    // Relasi Many-to-One dengan User
    // Banyak resep bisa dimiliki oleh satu User
    @ManyToOne // Menandai relasi Many-to-One
    @JoinColumn(name = "user_id", nullable = false) // Menentukan kolom foreign key 'user_id' yang tidak boleh null
    private User user; // Referensi ke entitas User yang memiliki resep ini

    @Column(nullable = false, length = 255) // Kolom 'name' tidak boleh null, panjang maks 255
    private String name; // Nama resep

    @Column(columnDefinition = "TEXT") // Memetakan ke tipe data TEXT di MySQL untuk deskripsi panjang
    private String description; // Deskripsi resep (opsional)

    @Column(name = "image_url", nullable = false, length = 255) // Kolom 'image_url' tidak boleh null, panjang maks 255
    private String imageUrl; // URL gambar resep

    @Column(name = "reference_link", nullable = false, length = 255) // Kolom 'reference_link' tidak boleh null, panjang maks 255
    private String referenceLink; // Tautan ke web luar untuk referensi lebih lanjut

    @CreationTimestamp // Mengisi otomatis timestamp saat entitas pertama kali disimpan
    @Column(name = "created_at", nullable = false, updatable = false) // Kolom 'created_at' tidak boleh null, tidak dapat diupdate manual
    private LocalDateTime createdAt; // Waktu pembuatan resep

    @UpdateTimestamp // Mengisi otomatis timestamp setiap kali entitas diperbarui
    @Column(name = "updated_at", nullable = false) // Kolom 'updated_at' tidak boleh null
    private LocalDateTime updatedAt; // Waktu terakhir pembaruan resep

    // Relasi One-to-Many dengan Ingredient
    // Satu resep bisa memiliki banyak Ingredient
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    // 'mappedBy' menunjukkan nama field di entitas Ingredient yang memiliki relasi ManyToOne ke Recipe ini.
    // 'cascade = CascadeType.ALL' berarti operasi persist/merge/remove pada Recipe akan di-cascade ke Ingredient terkait.
    // 'orphanRemoval = true' berarti jika Ingredient dihapus dari daftar 'ingredients' ini, maka akan dihapus dari database.
    private List<Ingredient> ingredients = new ArrayList<>(); // Inisialisasi untuk menghindari NullPointerException

    // --- Konstruktor (Diperlukan oleh JPA, terutama konstruktor tanpa argumen) ---
    // Jika menggunakan Lombok, Anda dapat menghapus konstruktor manual ini
    public Recipe() {
    }

    // Konstruktor dengan parameter untuk pembuatan objek Recipe baru
    public Recipe(User user, String name, String description, String imageUrl, String referenceLink) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.referenceLink = referenceLink;
    }

    // --- Getters dan Setters untuk semua field ---
    // Jika menggunakan Lombok, Anda dapat menghapus getter/setter manual ini

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReferenceLink() {
        return referenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // --- Helper Methods untuk manajemen relasi bidirectional (penting!) ---
    // Ketika menambah atau menghapus ingredient dari sebuah resep,
    // penting untuk mengatur sisi 'recipe' pada objek Ingredient juga.
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.setRecipe(null); // Memutus relasi baliknya
    }

    // --- Metode toString (sangat direkomendasikan untuk debugging) ---
    // Jika menggunakan Lombok, @Data akan otomatis membuat toString()
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", referenceLink='" + referenceLink + '\'' +
                // Hindari mencetak objek 'user' atau 'ingredients' secara langsung di toString()
                // karena dapat menyebabkan StackOverflowError jika ada siklus di relasi bidirectional
                ", userId=" + (user != null ? user.getId() : "null") +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
