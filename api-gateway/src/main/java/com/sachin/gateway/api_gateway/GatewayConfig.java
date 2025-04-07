package com.sachin.gateway.api_gateway;

import com.sachin.gateway.api_gateway.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        System.out.println("GatewayConfig initialized");

        return builder.routes()

                // Protect user-service routes
                //ALLOW register & login without token
                .route("user-auth", r -> r.path("/api/auth/register", "/api/auth/login")
                        .and()
                        .method("OPTIONS", "POST") // 👈 Handle preflight and login POST
                        .uri("lb://user-service"))

                .route("user-service-secure", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("lb://user-service"))

                // Protect course-service routes
                .route("course-service", r -> r.path("/api/courses/**")
                        .filters(f -> f.filter(jwtAuthFilter))

                        .uri("lb://course-service"))

                // Protect enrollment-service routes
                .route("enrollment-service", r -> r.path("/api/enrollments/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("lb://enrollment-service"))

                .build();
    }
}
