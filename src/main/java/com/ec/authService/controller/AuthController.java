package com.ec.authService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.ec.authService.Dto.UserRequest;
import com.ec.authService.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authservice;

    // 🔹 Register User
    @PostMapping("/register")
    public String register(@RequestBody UserRequest request) {
        return authservice.createUser(request);
    }

    // 🔹 Update User
    @PutMapping("/update/{userId}")
    public String update(@PathVariable String userId,
                         @RequestBody UserRequest request) {
        return authservice.updateUser(userId, request);
    }

    // @PostMapping("/register")
    // public ResponseEntity<String> register(@RequestBody Map<String, String> req) {
    //     String username = req.get("username");
    //     String password = req.get("password");
    //     String email = req.get("email");

    //     if (username == null || password == null || email == null) {
    //         return ResponseEntity.badRequest().body("username, password and email are required");
    //     }

    //     String result = service.registerUser(username, password, email);
    //     return ResponseEntity.ok(result);
    // }
}

