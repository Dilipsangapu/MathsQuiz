package com.QuizGmae.QuizGame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // Allow access to static resources
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // Allow quiz endpoints and home page
                        .requestMatchers("/", "/quiz/**").permitAll()

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Enable form login
                .formLogin(Customizer.withDefaults())

                // Enable logout (default config)
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirect after logout
                        .permitAll()
                )

                // Disable CSRF for simplicity (optional, not recommended for production)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
