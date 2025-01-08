package com.eventManagement.controller;

import com.eventManagement.dto.EventReportDTO;
import com.eventManagement.dto.GuestDTO;
import com.eventManagement.dto.VendorDTO;
import com.eventManagement.feignclient.VendorFeign;
import com.eventManagement.model.Event;
import com.eventManagement.model.VendorRequest;
import com.eventManagement.repository.VendorRequestRepository;
import com.eventManagement.service.EventService;
import com.eventManagement.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")

public class EventController
{
    @Autowired
    private EventService eventService;

    @Autowired
    private VendorRequestRepository vendorRequestRepository;
    @Autowired
    VendorFeign vendorFeign;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) throws EventNotFoundException {
        return eventService.getEventById(id);
    }

//    @PostMapping("/{eventId}/addVendor")
//    public void addVendorToEvent(@PathVariable Long eventId, @RequestParam Long vendorId) {
//        eventService.addVendorToEvent(eventId, vendorId);
//    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) throws EventNotFoundException {
        return eventService.updateEvent(id, updatedEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }


    @GetMapping("/search/name")
    public List<Event> searchEventsByName(@RequestParam String name) throws EventNotFoundException {
        return eventService.searchEventsByName(name);
    }

    @GetMapping("/search/dates")
    public List<Event> searchEventsByDateRange(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws EventNotFoundException {
        return eventService.searchEventsByDateRange(startDate, endDate);
    }

    @GetMapping("/getAllGuests/{eventId}")
    public List<GuestDTO> getAllGuestsByEventId(@PathVariable Long eventId){
        return eventService.getAllGuestsByEventId(eventId);
    }

    @GetMapping("/getEventReport/{eventId}")
    public EventReportDTO getEventReport(@PathVariable Long eventId)
    {
        return eventService.getEventReport(eventId);
    }

    @GetMapping("/user/{userId}")
    public List<Event> getEventsByUserId(@PathVariable Long userId) {
        return eventService.getEventsByUserId(userId);
    }

//    @PostMapping("/assignEventToClient")
//    public void assignEventToClient(@RequestParam Long eventId, @RequestParam Long clientId) {
//        eventService.assignEventToClient(eventId, clientId);
//    }

    @GetMapping("/vendors/{vendorId}")
    public List<Event> getEventsByVendorId(@PathVariable Long vendorId){
        return eventService.getEventsByVendorId(vendorId);
    }

//    @PostMapping("/{eventId}/request-vendor/{vendorId}")
//    public ResponseEntity<String> requestVendor(@PathVariable Long eventId, @PathVariable Long vendorId) {
//
//        // Check if the vendor exists in Vendor Service
//        VendorDTO vendorDTO = vendorFeign.getVendorById(vendorId);
//
//        if (vendorDTO == null) {
//            // If vendor does not exist, return an error message
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor with ID " + vendorId + " does not exist.");
//        }
//
//        // Check if a request for the same eventId and vendorId already exists
//        boolean requestExists = vendorRequestRepository.existsByEventIdAndVendorId(eventId, vendorId);
//
//        if (requestExists) {
//            // If the request already exists, return a bad request response
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A request for this vendor already exists for the event.");
//        }
//
//        // Create a new VendorRequest
//        VendorRequest vendorRequest = new VendorRequest();
//        vendorRequest.setEventId(eventId);
//        vendorRequest.setVendorId(vendorId);
//        vendorRequest.setStatus(VendorRequest.RequestStatus.PENDING);
//        vendorRequestRepository.save(vendorRequest);
//
//        // Call the Vendor service to create the event request (you can add any additional logic here)
//        vendorFeign.createEventRequest(eventId, vendorId);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body("Event request sent successfully");
//    }




}