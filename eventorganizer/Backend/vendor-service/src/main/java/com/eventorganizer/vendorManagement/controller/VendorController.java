package com.eventorganizer.vendorManagement.controller;


import com.eventorganizer.vendorManagement.dto.EventDTO;
import com.eventorganizer.vendorManagement.model.Vendor;
import com.eventorganizer.vendorManagement.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Create a new vendor
    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    // Get a vendor by ID
    @GetMapping("/{vendorId}")
    public Vendor getVendorById(@PathVariable Long vendorId) {
        Optional<Vendor> vendor = vendorService.getVendorById(vendorId);
        return vendor.orElse(null); // Return null if not found, Spring handles 404 response
    }

    // Get all vendors
    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    // Update a vendor
    @PutMapping("/{vendorId}")
    public Vendor updateVendor(@PathVariable Long vendorId, @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(vendorId, vendor);
        return updatedVendor; // Return null if not updated (Spring handles 404 response)
    }

    // Delete a vendor by ID
    @DeleteMapping("/{vendorId}")
    public void deleteVendor(@PathVariable Long vendorId) {
        vendorService.deleteVendor(vendorId);
        // Spring will automatically return 204 No Content
    }

    // Get vendors by service type (Optional)
    @GetMapping("/service/{serviceType}")
    public List<Vendor> getVendorsByServiceType(@PathVariable String serviceType) {
        return vendorService.getVendorsByServiceType(serviceType);
    }
//    @PutMapping("/{vendorId}/events/{eventId}")
//    public void respondToEventRequest(@PathVariable Long vendorId,
//                                      @PathVariable Long eventId,
//                                      @RequestParam boolean isAccepted) {
//        vendorService.respondToEventRequest(vendorId, eventId, isAccepted);
//    }

//    @PostMapping("/{vendorId}/accepted-events")
//    public ResponseEntity<Vendor> addAcceptedEvent(
//            @PathVariable Long vendorId,
//            @RequestBody EventDTO event) {
//        Vendor updatedVendor = vendorService.addAcceptedEvent(vendorId, event);
//        return ResponseEntity.ok(updatedVendor);
//    }

    @GetMapping("/{vendorId}/accepted-events")
    public List<Long> getAcceptedEvents(@PathVariable Long vendorId) {
        Optional<Vendor> vendor = vendorService.getVendorById(vendorId);
        if (vendor.isPresent()) {
            return vendorService.getAcceptedEvents(vendorId);
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<Vendor> getVendorByUserId(@PathVariable Long userId) {
        Optional<Vendor> vendor = vendorService.getVendorByUserId(userId);
        return vendor.map(ResponseEntity::ok)  // If found, return 200 OK with the vendor
                .orElseGet(() -> ResponseEntity.noContent().build());  // If not found, return 204 No Content
    }

    @GetMapping("/vendorIdByUseId/{userId}")
    public Long getVendorIdByUserId(@PathVariable Long userId)
    {
        return vendorService.getVendorIdByUserId(userId);
    }
}
