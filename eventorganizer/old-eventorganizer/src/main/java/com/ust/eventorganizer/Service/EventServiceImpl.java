package com.ust.eventorganizer.Service;
import com.ust.eventorganizer.Repository.*;
import com.ust.eventorganizer.exception.EventNotFoundException;
import com.ust.eventorganizer.exception.GuestNotFoundException;
import com.ust.eventorganizer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
public class EventServiceImpl implements EventService
{
    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private GuestRepository guestRepo;

    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private FeedbackRepository feedbackRepo;

    @Autowired
    private VendorRepository vendorRepo;

    @Override
    public Event saveEvent(Event event)
    {
        return eventRepo.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public Event getEventById(Long id) throws EventNotFoundException {
        return eventRepo.findById(id).orElseThrow(()->new EventNotFoundException("ID: "+id+" not found"));

    }
    //when you fetch an event, you also get the associated vendors:



    @Override
    public void deleteEvent(Long id)
    {
        eventRepo.deleteById(id);
    }


    public Event updateEvent(Long id, Event event) throws EventNotFoundException {

        if(!eventRepo.existsById(id))
        {
            throw new EventNotFoundException("Event with ID: "+id+" not found");
        }

        Event eventToUpdate = getEventById(id);
        if(event.getTitle()!=null)  //title date location
        {
            eventToUpdate.setTitle(event.getTitle());
        }
        if(event.getDate()!=null)
        {
            eventToUpdate.setDate(event.getDate());
        }
        if(event.getLocation()!=null)
        {
            eventToUpdate.setLocation(event.getLocation());
        }
        return eventRepo.save(eventToUpdate);
    }



    @Override
    public Event assignVendorToEvent(Long eventId, Long vendorId) {
        // Fetch vendor and event, handle not found with custom exceptions
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        event.getVendors().add(vendor); // Add vendor to event's vendor list
        vendor.getEvents().add(event);   // Add event to vendor's event list

        // Save the updated entities
        eventRepo.save(event);
        vendorRepository.save(vendor);

        return event;
    }

    public Event removeVendorFromEvent(Long eventId, Long vendorId) {
        // Fetch vendor and event, handle not found with custom exceptions
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Remove the vendor from the event's vendor list
        event.getVendors().remove(vendor);

        // Remove the event from the vendor's event list
        vendor.getEvents().remove(event);

        // Save the updated entities
        eventRepo.save(event);
        vendorRepository.save(vendor);

        return event;
    }

    @Override
    public List<Event> searchEventsByName(String name) throws EventNotFoundException {
        List<Event> events= eventRepo.findByTitleContainingIgnoreCase(name);
        if(events.isEmpty())
        {
            throw new EventNotFoundException("Event with name :"+name+" not found");
        }
        return events;
    }

    public List<Event> searchEventsByDateRange(LocalDate startDate, LocalDate endDate) throws EventNotFoundException {
        List<Event> events= eventRepo.findByDateBetween(startDate,endDate);
        if(events.isEmpty())
        {
            throw new EventNotFoundException("Event not found");
        }
        return events;
    }


    public Event assignGuestToEvent(Long eventId, Long guestId)
    {
        // Fetch guest and event, handle not found with custom exceptions
        Guest guest = guestRepo.findById(guestId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Add guest to event's guest list
        event.getGuests().add(guest);

        // Set the event in guest's entity to establish the relationship
        guest.setEvent(event);

        // Save the updated entities
        eventRepo.save(event);
        guestRepo.save(guest);

        return event;
    }

    public Event removeGuestFromEvent(Long eventId, Long guestId) {
        // Fetch guest and event, handle not found with custom exceptions
        Guest guest = guestRepo.findById(guestId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Remove the guest from the event's guest list
        event.getGuests().remove(guest);

        // Clear the event reference in guest to break the association
        guest.setEvent(null);

        // Save the updated entities
        eventRepo.save(event);
        guestRepo.save(guest);

        return event;
    }


    public Event assignFeedbackToEvent(Long eventId, Long feedbackId) {
        // Fetch feedback and event, handle not found with custom exceptions
        Feedback feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Set the event in feedback's entity to establish the relationship
        feedback.setEvent(event);

        // Add feedback to event's feedback list
        event.getFeedbacks().add(feedback);



        // Save the updated entities
        eventRepo.save(event);
        feedbackRepo.save(feedback);

        return event;
    }

    public Event removeFeedbackFromEvent(Long eventId, Long feedbackId) {
        // Fetch feedback and event, handle not found with custom exceptions
        Feedback feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Remove the feedback from the event's feedback list
        event.getFeedbacks().remove(feedback);

        // Clear the event reference in feedback to break the association
        feedback.setEvent(null);

        // Save the updated entities
        eventRepo.save(event);
        feedbackRepo.save(feedback);

        return event;
    }

    public Event assignBudgetToEvent(Long eventId, Long budgetId) {
        // Fetch budget and event, handle not found with custom exceptions
        Budget budget = budgetRepo.findById(budgetId)
                .orElseThrow(null);
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Assign budget to the event
        event.setBudget(budget);

        // Set the event in budget's entity to establish the relationship
        budget.setEvent(event);

        // Save the updated entities
        eventRepo.save(event);
        budgetRepo.save(budget);

        return event;
    }

    public Event removeBudgetFromEvent(Long eventId)
    {
        // Fetch the event, handle not found with custom exception
        Event event = eventRepo.findById(eventId)
                .orElseThrow(null);

        // Get the assigned budget, if any
        Budget budget = event.getBudget();

        if (budget != null) {
            // Remove the association from both sides
            event.setBudget(null);
            budget.setEvent(null);

            // Save the updated entities
            eventRepo.save(event);
            budgetRepo.save(budget);
        }

        return event;
    }


    // Method to calculate total vendor cost and update actualExpense in Budget
    public Event updateActualExpenseInBudget(Long eventId) throws EventNotFoundException
    {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Event with ID: " + eventId + " not found"));

        // Calculate the total vendor cost using BigDecimal
        BigDecimal totalCost = event.getVendors().stream()
                .map(Vendor::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Update actualExpense in the associated Budget
        if (event.getBudget() != null)
        {
            event.getBudget().setActualExpense(totalCost);
            budgetRepo.save(event.getBudget()); // Save updated Budget
        }
        else
        {
            throw new RuntimeException("Budget not found for Event ID: " + eventId);
        }

        // Save the updated event (optional if budget update is sufficient)
        return eventRepo.save(event);
    }


}
