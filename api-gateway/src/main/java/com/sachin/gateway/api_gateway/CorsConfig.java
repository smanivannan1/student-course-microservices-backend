package com.sachin.gateway.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://34.48.110.246:4200", "http://localhost:4200"));
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        System.out.println("✅ CorsWebFilter loaded");
        System.out.println("CORS config applied to path /** with:");
        System.out.println("  Origins: " + config.getAllowedOrigins());
        System.out.println("  Methods: " + config.getAllowedMethods());
        System.out.println("  Headers: " + config.getAllowedHeaders());
        return new CorsWebFilter(source);
    }
}