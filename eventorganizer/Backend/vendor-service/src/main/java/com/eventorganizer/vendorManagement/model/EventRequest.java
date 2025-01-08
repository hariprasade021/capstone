package com.eventorganizer.vendorManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Long vendorId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

}
