package com.eventManagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VendorRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Long vendorId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    public enum RequestStatus
    {
        PENDING,ACCEPTED,
        DECLINED
    }

}
