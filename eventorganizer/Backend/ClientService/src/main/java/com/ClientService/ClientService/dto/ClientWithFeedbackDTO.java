package com.ClientService.ClientService.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientWithFeedbackDTO
{
    private Long clientId;
    private String name;
    private String email;
    private List<EventWithFeedback> eventswithFeedback;

    @Data
    public static class EventWithFeedback { // Declare as static
        private Long eventId;
        private String feedback;
    }
}
