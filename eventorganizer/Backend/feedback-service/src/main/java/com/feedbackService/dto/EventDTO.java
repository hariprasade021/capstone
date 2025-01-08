package com.feedbackService.dto;

import lombok.Data;

@Data
public class EventDTO
{
    private Long eventId;
    private String name;
    private String location;
    private String description;
    private Double estimatedExpense;
    private Double actualExpense;
    private String status;
    private Long clientId; // Associated client ID
    private Long userId;
}
