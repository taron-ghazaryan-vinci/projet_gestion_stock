package com.taron.users.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("backend/users/uploads").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
