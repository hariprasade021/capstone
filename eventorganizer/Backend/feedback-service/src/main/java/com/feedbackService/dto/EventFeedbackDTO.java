package com.feedbackService.dto;

import lombok.Data;

@Data
public class EventFeedbackDTO {
    private Long clientId;
    private Long eventId;
    private String feedbackText;
}