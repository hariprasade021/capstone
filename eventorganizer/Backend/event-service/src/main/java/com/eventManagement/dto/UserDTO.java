package com.eventManagement.dto;

import lombok.Data;

@Data
public class UserDTO
{
    private Long id; // Unique identifier for the user
    private String username; // User's login name
    private String email;
    private String password; // Hashed password
    private String role;
}
