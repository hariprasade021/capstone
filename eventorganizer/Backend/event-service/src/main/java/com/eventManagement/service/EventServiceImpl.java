package com.eventManagement.service;

import com.eventManagement.dto.*;
import com.eventManagement.feignclient.*;
import com.eventManagement.model.Event;
import com.eventManagement.repository.EventRepository;
import com.eventManagement.exception.EventNotFoundException;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private VendorFeign vendorFeign;

    @Autowired
    private GuestFeign guestFeign;

    @Autowired
    private TaskFeign taskFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ClientFeign clientFeign;

    @Override
    public Event saveEvent(Event event) {
        UserDTO user = userFeign.getUserById(event.getUserId());
        if (user == null) {
            throw new RuntimeException("User with ID " + event.getUserId() + " does not exist.");
        }

        // Save the event and calculate actual expense
        Event savedEvent = eventRepo.save(event);
        savedEvent.setActualExpense(fetchTotalValue(savedEvent));
        return eventRepo.save(savedEvent);
    }

//    @Override
//    public void addVendorToEvent(Long eventId, Long vendorId) {
//        // Retrieve the event
//        Event event = eventRepo.findById(eventId)
//                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
//
//        // Add the vendor ID to the event's vendor list
//        if (!event.getVendors().contains(vendorId)) {
//            event.getVendors().add(vendorId);
//            eventRepo.save(event);  // Save the updated event
//        }
//    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = eventRepo.findAll();
        for (Event event : events) {
            event.setActualExpense(fetchTotalValue(event));
        }
        return events;
    }

    @Override
    public Event getEventById(Long id) throws EventNotFoundException {
        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new EventNotFoundException("ID: " + id + " not found"));
        event.setActualExpense(fetchTotalValue(event));
        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Event updateEvent(Long id, Event updatedEvent) throws EventNotFoundException {
        // Find the existing event or throw an exception
        log.info(updatedEvent.toString());
        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID: " + id + " not found"));

        // Update basic event details
        if (updatedEvent.getName() != null) {
            existingEvent.setName(updatedEvent.getName());
        }
        if (updatedEvent.getEstimatedExpense() != null) {
            existingEvent.setEstimatedExpense(updatedEvent.getEstimatedExpense());
        }
        if (updatedEvent.getActualExpense() != null) {
            existingEvent.setActualExpense(updatedEvent.getActualExpense());
        }
        if (updatedEvent.getLocation() != null) {
            existingEvent.setLocation(updatedEvent.getLocation());
        }
        if (updatedEvent.getDescription() != null) {
            existingEvent.setDescription(updatedEvent.getDescription());
        }
        if (updatedEvent.getStartDate() != null) {
            existingEvent.setStartDate(updatedEvent.getStartDate());
        }
        if (updatedEvent.getEndDate() != null) {
            existingEvent.setEndDate(updatedEvent.getEndDate());
        }
        if (updatedEvent.getType() != null) {
            existingEvent.setType(updatedEvent.getType());
        }
        if (updatedEvent.getStatus() != null) {
            existingEvent.setStatus(updatedEvent.getStatus());
        }
        if (updatedEvent.getUserId() != null) {
            existingEvent.setUserId(updatedEvent.getUserId());
        }

        // Update Vendor List
        if (updatedEvent.getVendors() != null) {
            // Validate vendor IDs by attempting to retrieve them
            for (Long vendorId : updatedEvent.getVendors()) {
                try {
                    vendorFeign.getVendorById(vendorId);
                    existingEvent.getVendors().add(vendorId);
                } catch (Exception e) {
                    throw new RuntimeException("Vendor with ID: " + vendorId + " not found");
                }
            }


        }

        // Update Guest List
//        if (updatedEvent.getGuests() != null) {
//            // Validate guest IDs by attempting to retrieve them
//            for (Long guestId : updatedEvent.getGuests()) {
//                try {
//                    guestFeign.getGuestById(guestId);
//                } catch (Exception e) {
//                    throw new RuntimeException("Guest with ID: " + guestId + " not found");
//                }
//            }
//
//            // Create a new list with the updated guests
//            existingEvent.setGuests(new ArrayList<>(updatedEvent.getGuests()));
//        }

        // Recalculate actual expense
        existingEvent.setActualExpense(fetchTotalValue(existingEvent));

        // Save and return the updated event
        return eventRepo.save(existingEvent);
    }



    @Override
    public List<Event> searchEventsByName(String name) throws EventNotFoundException {
        List<Event> events = eventRepo.findByNameContainingIgnoreCase(name);
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found with name: " + name);
        }
        return events;
    }

    @Override
    public List<Event> searchEventsByDateRange(LocalDate startDate, LocalDate endDate) throws EventNotFoundException {
        List<Event> events = eventRepo.findByStartDateBetween(startDate, endDate);
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found between dates: " + startDate + " and " + endDate);
        }
        return events;
    }

    public double fetchTotalValue(Event event) {
        double totalValue = 0;
        for (Long vendorId : event.getVendors()) {
            VendorDTO vendor = vendorFeign.getVendorById(vendorId);
            if (vendor != null) {
                totalValue += vendor.getVendorAmount();
            } else {
                System.out.println("Vendor with ID " + vendorId + " not found.");
            }
        }
        return totalValue;
    }

    @Override
    public List<GuestDTO> getAllGuestsByEventId(Long id) {
        Optional<Event> optionalEvent = eventRepo.findById(id);

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException("Event with ID: " + id + " not found.");
        }

        Event event = optionalEvent.get();

        List<Long> guestIds = Optional.ofNullable(event.getGuests()).orElse(List.of());

        return guestIds.stream()
                .map(guestId -> guestFeign.getGuestById(guestId))
                .collect(Collectors.toList());
    }

    @Override
    public List<VendorDTO> getAllVendorsByEventId(Long id) {
        Optional<Event> optionalEvent = eventRepo.findById(id);

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException("Event with ID: " + id + " not found.");
        }

        Event event = optionalEvent.get();

        Set<Long> vendorIds = Optional.ofNullable(event.getVendors()).orElse(Set.of());

        return vendorIds.stream()
                .map(vendorFeign::getVendorById)
                .collect(Collectors.toList());
    }

    @Override
    public EventReportDTO getEventReport(Long eventId) {
        Optional<Event> optionalEvent = eventRepo.findById(eventId);

        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException("Event with ID: " + eventId + " not found.");
        }

        Event event = optionalEvent.get();

        // Map Event to EventDTO
        EventDTO eventDetails = new EventDTO();
        eventDetails.setId(event.getId());
        eventDetails.setName(event.getName());
        eventDetails.setTitle(event.getTitle());
        eventDetails.setLocation(event.getLocation());
        eventDetails.setDescription(event.getDescription());
        eventDetails.setEstimatedExpense(event.getEstimatedExpense());
        eventDetails.setActualExpense(event.getActualExpense());
        eventDetails.setStartDate(event.getStartDate());
        eventDetails.setEndDate(event.getEndDate());
        eventDetails.setType(event.getType());
        eventDetails.setStatus(event.getStatus());

        // Fetch guest details
        List<Long> guestIds = Optional.ofNullable(event.getGuests()).orElse(List.of());
        List<GuestDTO> guestDetails = guestIds.stream()
                .map(guestId -> guestFeign.getGuestById(guestId))
                .collect(Collectors.toList());

        // Fetch vendor details
        Set<Long> vendorIds = Optional.ofNullable(event.getVendors()).orElse(Set.of());
        List<VendorDTO> vendorDetails = vendorIds.stream()
                .map(vendorId -> vendorFeign.getVendorById(vendorId))
                .collect(Collectors.toList());

        // Fetch task details
        List<TaskDTO> taskDetails = Optional.ofNullable(taskFeign.getTasksByEventId(eventId)).orElse(List.of());

        EventReportDTO eventReport = new EventReportDTO();
        eventReport.setEventDetails(eventDetails);
        eventReport.setGuestDetails(guestDetails);
        eventReport.setVendorDetails(vendorDetails);
        eventReport.setTaskDetails(taskDetails);

        return eventReport;
    }

    @Override
    public List<Event> getEventsByUserId(Long userId) {
        return eventRepo.findByUserId(userId);
    }
    @Override
    public List<Event> getEventsByVendorId(Long vendorId) {
        // Retrieve all events and filter those containing the specified vendorId
        List<Event> events = eventRepo.findAll().stream()
                .filter(event -> event.getVendors() != null && event.getVendors().contains(vendorId))
                .collect(Collectors.toList());

        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found for vendor ID: " + vendorId);
        }

        // Update actual expenses for the events before returning
        for (Event event : events) {
            event.setActualExpense(fetchTotalValue(event));
        }

        return events;
    }

}
