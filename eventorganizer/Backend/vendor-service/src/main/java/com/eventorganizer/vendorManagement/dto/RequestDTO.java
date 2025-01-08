package com.eventorganizer.vendorManagement.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RequestDTO
{
    private long requestId;
    private long vendorId;
    private long eventId;


    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

}
