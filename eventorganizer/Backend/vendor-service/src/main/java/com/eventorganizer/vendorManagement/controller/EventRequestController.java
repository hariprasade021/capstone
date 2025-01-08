package com.eventorganizer.vendorManagement.controller;

import com.eventorganizer.vendorManagement.dto.EventDTO;
import com.eventorganizer.vendorManagement.model.EventRequest;
import com.eventorganizer.vendorManagement.repository.EventRequestRepository;
import com.eventorganizer.vendorManagement.service.EventRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/vendors")
public class EventRequestController {

    @Autowired
    private EventRequestService eventRequestService;

    @Autowired
    private EventRequestRepository eventRequestRepository;

    /**
     * View all pending requests for a specific vendor.
     *
     * @param vendorId ID of the vendor
     * @return List of pending requests
     */
    @GetMapping("/{vendorId}/requests")
    public ResponseEntity<List<EventRequest>> viewRequests(@PathVariable Long vendorId) {
        // Fetch all pending requests for the vendor
        List<EventRequest> eventRequests = eventRequestRepository.findByVendorIdAndStatus(
                vendorId,
                EventRequest.RequestStatus.PENDING
        );

        // Map EventRequest entities to EventRequestDto
        List<EventRequest> requestsDtoList = eventRequests.stream()
                .map(request -> new EventRequest(
                        request.getId(),
                        request.getEventId(),
                        request.getVendorId(),
                        request.getStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(requestsDtoList);
    }

    /**
     * Respond to a specific event request.
     *
     * @param vendorId  ID of the vendor
     * @param requestId ID of the request
     * @param response  Response to the request (ACCEPT/DECLINE)
     * @return ResponseEntity with a result message
     */
    @PostMapping("/{vendorId}/requests/{requestId}")
    public ResponseEntity<String> respondToRequest(@PathVariable Long vendorId,
                                                   @PathVariable Long requestId,
                                                   @RequestParam("response") String response) {
        // Delegate request handling to the service
        return eventRequestService.requestHandler(vendorId, requestId, response);
    }



    @PostMapping("/create/{eventId}/request/{vendorId}")
    public EventRequest createEventRequest(@RequestParam Long eventId, @RequestParam Long vendorId) {
        return eventRequestService.createEventRequest(eventId, vendorId);
    }
}
