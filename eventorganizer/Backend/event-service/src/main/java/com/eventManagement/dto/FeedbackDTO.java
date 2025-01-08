package com.eventManagement.dto;

import lombok.Data;

@Data
public class FeedbackDTO
{
    private Long feedbackId;
    private Long clientId; // Associated client ID
    private Long eventId; // Associated event ID
    private Long userId; // User receiving feedback
    private String feedbackText;
}
