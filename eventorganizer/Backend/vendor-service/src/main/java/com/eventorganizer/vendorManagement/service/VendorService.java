package com.eventorganizer.vendorManagement.service;

import com.eventorganizer.vendorManagement.dto.EventDTO;
import com.eventorganizer.vendorManagement.model.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorService
{
    Vendor createVendor(Vendor vendor);

    Optional<Vendor> getVendorById(Long vendorId);

    List<Vendor> getAllVendors();

    Vendor updateVendor(Long vendorId, Vendor vendor);

    void deleteVendor(Long vendorId);

    List<Vendor> getVendorsByServiceType(String serviceType);

//    Vendor addAcceptedEvent(Long vendorId, EventDTO event);
////    public void respondToEventRequest(Long vendorId, Long eventId, boolean isAccepted);
//    public List<EventDTO> getAcceptedEvents(Long vendorId);
    public List<Long> getAcceptedEvents(Long vendorId);

    public Optional<Vendor> getVendorByUserId(Long userId);

    public Long getVendorIdByUserId(Long userId);
}
