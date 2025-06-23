package com.faiz.buku_resep_app.repository;

import com.faiz.buku_resep_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Add this method to find a user by their email
    Optional<User> findByEmail(String email);

    // This method can also be useful
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
