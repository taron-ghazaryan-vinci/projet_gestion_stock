package com.taron.authentications.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Autorise toutes les routes de l'application à accepter les requêtes CORS depuis http://localhost:4200
        registry.addMapping("/**") // Applique cette configuration à toutes les routes
                .allowedOrigins("http://localhost:4200")  // Autoriser les requêtes venant de ton frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // Méthodes HTTP autorisées
                .allowedHeaders("*");  // Autorise tous les en-têtes
    }
}
