package com.eventorganizer.vendorManagement.service;

import com.eventorganizer.vendorManagement.dto.EventDTO;
import com.eventorganizer.vendorManagement.dto.UserDTO;
import com.eventorganizer.vendorManagement.feign.EventFeign;
import com.eventorganizer.vendorManagement.feign.UserFeign;
import com.eventorganizer.vendorManagement.model.Vendor;
import com.eventorganizer.vendorManagement.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService
{
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    UserFeign userFeign;
    @Autowired
    EventFeign eventFeign;

    //    @Override
//    public Vendor createVendor(Vendor vendor) {
//
//        UserDTO user = userFeign.getUserById(vendor.getUserId());
//        if (user == null) {
//            throw new RuntimeException("User with ID " + vendor.getUserId() + " does not exist.");
//        }
//
//        return vendorRepository.save(vendor);
//
//    }
    @Override
    public Vendor createVendor(Vendor vendor) {
        // Check if the user exists using Feign client
        UserDTO user = userFeign.getUserById(vendor.getUserId());
        if (user == null) {
            throw new RuntimeException("User with ID " + vendor.getUserId() + " does not exist.");
        }

        // Check if a vendor profile already exists for this userId
        Optional<Vendor> existingVendor = vendorRepository.findByUserId(vendor.getUserId());
        if (existingVendor.isPresent()) {
            throw new RuntimeException("A vendor profile for User ID " + vendor.getUserId() + " already exists.");
        }

        // Save the new vendor profile
        return vendorRepository.save(vendor);
    }

//    @Override
//    public void respondToEventRequest(Long vendorId, Long eventId, boolean isAccepted)
//    {
//        Vendor vendor = vendorRepository.findById(vendorId)
//                .orElseThrow(() -> new RuntimeException("Vendor not found"));
//
//        EventDTO event = eventFeign.getEventById(eventId);
//        if (event == null) {
//            throw new RuntimeException("Event not found");
//        }
//
//        if (isAccepted) {
//            // Prevent duplicate acceptance
//            if (!vendor.getAcceptedEventIds().contains(eventId)) {
//                vendor.getAcceptedEventIds().add(eventId);
//                vendorRepository.save(vendor);
//
//                // Update the Event to add the vendorId
//                event.getVendorIds().add(vendorId);
//                eventFeign.updateEvent(event);
//            }
//        }
//    }

//    @Override
//    public void respondToEventRequest(Long vendorId, Long eventId, boolean isAccepted) {
//        // Fetch the vendor from the repository
//        Vendor vendor = vendorRepository.findById(vendorId)
//                .orElseThrow(() -> new RuntimeException("Vendor not found"));
//
//        // Fetch the event using Feign client
//        EventDTO event = eventFeign.getEventById(eventId); // Fetch the event
//
//        // Event validation (In case event is not found, the Feign client should handle this)
//        if (event == null) {
//            throw new RuntimeException("Event not found");
//        }
//
//        // Handle vendor acceptance
//        if (isAccepted) {
//            // Add vendor to event if not already added
//            if (!event.getVendorIds().contains(vendorId)) {
//                event.getVendorIds().add(vendorId);  // Add vendor to event's vendorIds list
//                vendor.getAcceptedEventIds().add(eventId);  // Add event ID to vendor's accepted events list
//
//                // Save the vendor updates to the repository
//                vendorRepository.save(vendor);
//
//                // Update the event through Feign client to reflect the new vendor
//                eventFeign.updateEvent(eventId, event);  // Send the updated event back to the event service
//            }
//        } else {
//            // If the vendor rejected the event
//            if (event.getVendorIds().contains(vendorId)) {
//                event.getVendorIds().remove(vendorId);  // Remove vendor from event's vendorIds list
//                vendor.getAcceptedEventIds().remove(eventId);  // Remove event ID from vendor's accepted events list
//
//                // Save the vendor updates to the repository
//                vendorRepository.save(vendor);
//
//                // Update the event through Feign client to reflect the removed vendor
//                eventFeign.updateEvent(eventId, event);  // Send the updated event back to the event service
//            }
//        }
//    }

//    @Override
//    public void respondToEventRequest(Long vendorId, Long eventId, boolean isAccepted) {
//        Vendor vendor = vendorRepository.findById(vendorId)
//                .orElseThrow(() -> new RuntimeException("Vendor not found"));
//
//        // Fetch the event using Feign client
//        EventDTO event = eventFeign.getEventById(eventId); // Fetch the event
//
//        // Event validation (In case event is not found, the Feign client should handle this)
//        if (event == null) {
//            throw new RuntimeException("Event not found");
//        }
//
//        // Handle vendor acceptance
//        if (isAccepted) {
//            // Add vendor to event if not already added
//            if (!event.getVendorIds().contains(vendorId)) {
//                event.getVendorIds().add(vendorId);  // Add vendor to event
//                eventFeign.updateEvent(eventId, event);  // Pass both the eventId and updated event to Feign client
//                vendor.getAcceptedEventIds().add(eventId);  // Add event ID to vendor's accepted list
//
//                EventDTO eventDTO = eventFeign.getEventById(eventId);
//                eventDTO.getVendorIds().add(vendorId);  // Assuming EventDTO has a set of vendorIds
//                eventFeign.updateEvent(eventId, eventDTO);
//                vendorRepository.save(vendor);  // Save vendor updates
//            }
//        } else {
//            // If the vendor rejected the event
//            if (event.getVendorIds().contains(vendorId)) {
//                event.getVendorIds().remove(vendorId);  // Remove vendor from event
//                eventFeign.updateEvent(eventId, event);  // Pass both the eventId and updated event to Feign client
//                vendor.getAcceptedEventIds().remove(eventId);  // Remove event ID from vendor's accepted list
//                vendorRepository.save(vendor);  // Save vendor updates
//
//
//            }
//        }
//    }


    @Override
    public Optional<Vendor> getVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor updateVendor(Long vendorId, Vendor vendor) {
        if (vendorRepository.existsById(vendorId)) {
            vendor.setVendorId(vendorId); // Make sure the vendorId remains the same during update
            return vendorRepository.save(vendor);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteVendor(Long vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    @Override
    public List<Vendor> getVendorsByServiceType(String serviceType) {
        return vendorRepository.findByVendorServiceType(serviceType);
    }

    public List<Long> getAcceptedEvents(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return vendor.getAcceptedEventIds();
    }

    @Override
    public Optional<Vendor> getVendorByUserId(Long userId) {
        return vendorRepository.findByUserId(userId);
    }

    @Override
    public Long getVendorIdByUserId(Long userId) {
        return vendorRepository.findByUserId(userId)
                .map(Vendor::getVendorId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found for user ID: " + userId));
    }

}