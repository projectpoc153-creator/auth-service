package com.ec.authService.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
// public class KeycloakConfig {

//     @Bean
//     public Keycloak keycloakClient(
//             @Value("${keycloak.server-url}") String serverUrl,
//             @Value("${keycloak.admin-username}") String username,
//             @Value("${keycloak.admin-password}") String password) {

//         return KeycloakBuilder.builder()
//                 .serverUrl(serverUrl)
//                 .realm("master")
//                 .clientId("admin-cli")
//                 .username(username)
//                 .password(password)
//                 .grantType(OAuth2Constants.PASSWORD)
//                 .build();
//     }
// }

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8180")
                .realm("master") // admin login always master
                .clientId("admin-cli")
                .username("admin")
                .password("admin123")
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
