package com.eventManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientDTO
{
    private Long clientId;
    private String name;
    private String email;
    private String phoneNumber;
    private Long userId;
}
