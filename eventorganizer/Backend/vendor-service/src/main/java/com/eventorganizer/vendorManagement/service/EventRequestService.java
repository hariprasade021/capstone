package com.eventorganizer.vendorManagement.service;

import com.eventorganizer.vendorManagement.dto.EventDTO;
import com.eventorganizer.vendorManagement.dto.RequestDTO;
import com.eventorganizer.vendorManagement.feign.EventFeign;
import com.eventorganizer.vendorManagement.model.EventRequest;
import com.eventorganizer.vendorManagement.model.Vendor;
import com.eventorganizer.vendorManagement.repository.EventRequestRepository;
import com.eventorganizer.vendorManagement.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class EventRequestService {

    @Autowired
    private EventRequestRepository eventRequestRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EventFeign eventFeign;

    public ResponseEntity<String> requestHandler(Long vendorId, Long requestId, String response) {
        // Fetch the event request
        Optional<EventRequest> requestOpt = eventRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) {
            return new ResponseEntity<>("Request not found", HttpStatus.NOT_FOUND);
        }

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // Fetch the event request
        EventRequest request = requestOpt.get();

        // Validate vendor ID in the request
        if (!(request.getVendorId()==vendorId)) {
            return new ResponseEntity<>("You cannot respond to this request", HttpStatus.FORBIDDEN);
        }

        // Handle the response to the request
        try {
            if ("ACCEPT".equalsIgnoreCase(response)) {
                // Check if the event already exists in the vendor's accepted event list
                if (!vendor.getAcceptedEventIds().contains(request.getEventId())) {
                    // Add event ID to vendor's accepted event list
                    vendor.getAcceptedEventIds().add(request.getEventId());

                    EventDTO event = eventFeign.getEventById(request.getEventId());
                    event.getVendorIds().add(vendorId);  // Add vendor to event's vendorIds list


                    // Update the event through Feign client to reflect the new vendor
                    eventFeign.updateEvent(request.getEventId(), event);
                    System.out.println("event updated");

                    vendorRepository.save(vendor); // Save updated vendor
                }

                request.setStatus(EventRequest.RequestStatus.ACCEPTED);
            } else if ("DECLINE".equalsIgnoreCase(response)) {
                request.setStatus(EventRequest.RequestStatus.DECLINED);
            } else {
                return new ResponseEntity<>("Invalid response", HttpStatus.BAD_REQUEST);
            }

            // Save the updated request
            eventRequestRepository.save(request);



            return new ResponseEntity<>("Request " + response + "ed successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public EventRequest createEventRequest(Long eventId, Long vendorId) {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setEventId(eventId);
        eventRequest.setVendorId(vendorId);
        eventRequest.setStatus(EventRequest.RequestStatus.PENDING); // Default status is PENDING

        return eventRequestRepository.save(eventRequest);
    }
}