package com.ec.authService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity   // enables @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 🔴 Disable CSRF (for APIs)
            .csrf(csrf -> csrf.disable())

            // 🔐 Authorization rules
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/auth/register",   // allow user registration
                            "/auth/login",      // optional
                            "/error"
                    ).permitAll()
                    .anyRequest().authenticated()
            )

            // 🔐 OAuth2 Login (redirect to Keycloak)
            // .oauth2Login(oauth2 -> oauth2
            //         .defaultSuccessUrl("/home", true)
            // )

            // 🔐 JWT validation (for API calls)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})
            );

        return http.build();
    }
}
