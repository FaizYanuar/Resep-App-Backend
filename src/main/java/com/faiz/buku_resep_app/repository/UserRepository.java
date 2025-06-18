package com.faiz.buku_resep_app.repository;

import com.faiz.buku_resep_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
