package com.sachin.submission.security;

import com.sachin.submission.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @jakarta.annotation.PostConstruct
    public void init() {
        System.out.println("JwtAuthenticationFilter INITED in " + this.getClass().getSimpleName());
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("JWT Filter triggered for path: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // remove "Bearer "
        userEmail = jwtService.extractUsername(jwt);
        Long userId = jwtService.extractUserId(jwt);

        var currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (userId != null &&
                (currentAuth == null || !currentAuth.isAuthenticated() || currentAuth.getPrincipal().equals("anonymousUser"))) {
            String role = jwtService.extractRole(jwt);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of(authority));

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            System.out.println("Setting authentication for: " + userEmail + " with role: " + role);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("SecurityContext set. Authorities = " + authToken.getAuthorities());

        }


        filterChain.doFilter(request, response);
    }
}
