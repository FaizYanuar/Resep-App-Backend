package com.faiz.buku_resep_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // This @Bean annotation is what creates the PasswordEncoder
        // that your AuthController is looking for.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // This configuration disables the default login page and session management,
        // which is what we want for a stateless REST API that communicates with React.
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for stateless APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow all requests to /api/auth/
                        .requestMatchers("/api/recipes/**").permitAll() // Temporarily allow all recipe requests
                        .anyRequest().authenticated() // Secure all other endpoints
                );
        return http.build();
    }
}
