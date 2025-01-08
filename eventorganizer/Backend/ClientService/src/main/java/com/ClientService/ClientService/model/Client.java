package com.ClientService.ClientService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId; // Unique identifier for each client
    private String name;
    private String email;
    private String phoneNumber; // Client's phone number
    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> events;

//    private String feedback;
}
