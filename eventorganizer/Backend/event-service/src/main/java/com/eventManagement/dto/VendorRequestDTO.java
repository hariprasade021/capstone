package com.eventManagement.dto;

import com.eventManagement.model.VendorRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class VendorRequestDTO
{
    private Long id;

    private Long eventId;
    private Long vendorId;

    @Enumerated(EnumType.STRING)
    private VendorRequest.RequestStatus status;


    public enum RequestStatus
    {
        PENDING,ACCEPTED,
        DECLINED
    }
}
