package com.eventManagement.dto;

import lombok.Data;

@Data
public class VendorDTO
{
    private Long vendorId;
    private String vendorCompanyName;
    private String vendorServiceType;
    private double vendorAmount;
//    private PaymentStatus vendorPaymentStatus;
}
