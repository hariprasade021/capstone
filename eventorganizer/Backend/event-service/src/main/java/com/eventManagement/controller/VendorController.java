package com.eventManagement.controller;

import com.eventManagement.dto.VendorDTO;
import com.eventManagement.feignclient.VendorFeign;
import com.eventManagement.model.VendorRequest;
import com.eventManagement.repository.VendorRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class VendorController
{
    @Autowired
    private VendorRequestRepository vendorRequestRepository;
    @Autowired
    VendorFeign vendorFeign;


    /**
     * Request vendor for an event.
     * Ensures that a request is not already made for the same eventId and vendorId combination.
     *
     * @param eventId The ID of the event.
     * @param vendorId The ID of the vendor.
     * @return Response indicating the result of the request.
     */
    @PostMapping("/{eventId}/request-vendor/{vendorId}")
    public ResponseEntity<String> requestVendor(@PathVariable Long eventId, @PathVariable Long vendorId) {

        // Check if the vendor exists in Vendor Service
        VendorDTO vendorDTO = vendorFeign.getVendorById(vendorId);

        if (vendorDTO == null) {
            // If vendor does not exist, return an error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor with ID " + vendorId + " does not exist.");
        }

        // Check if a request for the same eventId and vendorId already exists
        boolean requestExists = vendorRequestRepository.existsByEventIdAndVendorId(eventId, vendorId);

        if (requestExists) {
            // If the request already exists, return a bad request response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A request for this vendor already exists for the event.");
        }

        // Create a new VendorRequest
        VendorRequest vendorRequest = new VendorRequest();
        vendorRequest.setEventId(eventId);
        vendorRequest.setVendorId(vendorId);
        vendorRequest.setStatus(VendorRequest.RequestStatus.PENDING);
        vendorRequestRepository.save(vendorRequest);

        // Call the Vendor service to create the event request (you can add any additional logic here)
        vendorFeign.createEventRequest(eventId, vendorId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Event request sent successfully");
    }
}