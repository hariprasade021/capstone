package com.eventorganizer.vendorManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Vendor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    private Long userId;
    private String vendorCompanyName;
    private String vendorServiceType;
    private String vendorName;
    private String vendorPhone;
    private String vendorEmail;
    private double vendorAmount;
//    private PaymentStatus vendorPaymentStatus;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> acceptedEventIds = new ArrayList<>();
    //payment system to be done
}
