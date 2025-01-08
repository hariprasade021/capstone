package com.eventorganizer.apigateway.model;

import lombok.Data;
@Data
public class UserDTO {

    private Long id; // Unique identifier for the user
    private String username; // User's login name
    private String password; // Hashed password
    private Role role;
}
