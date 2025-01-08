package com.guestManagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Guest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Diet diet;

    @Enumerated(EnumType.STRING)
    private Rsvp rsvpStatus;

}
