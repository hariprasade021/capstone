package com.UserService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_data")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Unique identifier for the user
    private String username; // User's login name
//    private String email;
    private String password; // Hashed password
    @Enumerated(EnumType.STRING)
    private Role role;
}
