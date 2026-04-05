package com.ec.authService.service;


import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import com.ec.authService.Dto.UserRequest;
import com.ec.authService.entity.UserEntity;
import com.ec.authService.repository.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Keycloak keycloak;

    private final String realm = "projectpoc";

    private final UserRepository userRepository;

    // CREATE USER
    public String createUser(UserRequest request) {

    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());

    // Add custom attributes
    Map<String, List<String>> attributes = new HashMap<>();
    attributes.put("phoneNumber", List.of(request.getPhoneNumber()));
    attributes.put("address", List.of(request.getAddress()));

    user.setAttributes(attributes);

    Response response = keycloak.realm(realm).users().create(user);

    if (response.getStatus() != 201) {
        throw new RuntimeException("User creation failed");
    }

    String userId = response.getLocation()
            .getPath()
            .replaceAll(".*/([^/]+)$", "$1");

    // 🔐 Set password
    CredentialRepresentation password = new CredentialRepresentation();
    password.setType(CredentialRepresentation.PASSWORD);
    password.setValue(request.getPassword());
    password.setTemporary(false);

    keycloak.realm(realm)
            .users()
            .get(userId)
            .resetPassword(password);

    // Save in DB
    UserEntity entity = new UserEntity();
    entity.setUsername(request.getUsername());
    entity.setEmail(request.getEmail());
    entity.setFirstName(request.getFirstName());
    entity.setLastName(request.getLastName());
    entity.setPhoneNumber(request.getPhoneNumber());
    entity.setAddress(request.getAddress());
    entity.setKeycloakUserId(userId);

    userRepository.save(entity);

    return "User registered successfully";
    }

    // ✅ UPDATE USER
    public String updateUser(String userId, UserRequest request) {

        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(userId);

        UserRepresentation user = userResource.toRepresentation();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userResource.update(user);

        return "User updated successfully";
    }
}





// import org.keycloak.admin.client.Keycloak;
// import org.keycloak.representations.idm.CredentialRepresentation;
// import org.keycloak.representations.idm.UserRepresentation;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import jakarta.ws.rs.core.Response;

// @Service
// public class AuthService {

//     @Autowired
//     private Keycloak keycloak;

//     @Value("${keycloak.realm}")
//     private String realm;

//     public String registerUser(String username, String password, String email) {
//         UserRepresentation user = new UserRepresentation();
//         user.setUsername(username);
//         user.setEmail(email);
//         user.setEnabled(true);

//         Response response = keycloak.realm(realm).users().create(user);
//         if (response.getStatus() != 201 && response.getStatus() != 204) {
//             throw new RuntimeException("Keycloak user creation failed: HTTP " + response.getStatus());
//         }

//         String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

//         CredentialRepresentation credential = new CredentialRepresentation();
//         credential.setType(CredentialRepresentation.PASSWORD);
//         credential.setValue(password);
//         credential.setTemporary(false);

//         keycloak.realm(realm)
//                 .users()
//                 .get(userId)
//                 .resetPassword(credential);

//         return "User created: " + userId;
//     }
// }
