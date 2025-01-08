package com.eventManagement.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class GuestDTO
{
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Diet diet;
    @Enumerated(EnumType.STRING)
    private Rsvp rsvpStatus;

}
