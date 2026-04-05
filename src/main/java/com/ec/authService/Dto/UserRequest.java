package com.ec.authService.Dto;

import lombok.Data;

@Data
public class UserRequest {
     private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    private String phoneNumber;
    private String address;
}
